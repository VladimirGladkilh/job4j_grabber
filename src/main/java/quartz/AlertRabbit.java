package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    public static void main(String[] args) {
        RabbitParams params = new RabbitParams();

        try (Connection cn = params.getConnection();){
            int interval = params.getWorkInterval();
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put("store", store);
            data.put("connection", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();

            System.out.println(store);
        }  catch (Exception se) {
            se.printStackTrace();
        }

    }

    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            String s = writeToRabbit(connection);
            store.add(Long.parseLong(s));
        }
        private String writeToRabbit(Connection cn) {
            try (PreparedStatement st = cn.prepareStatement("insert into rabbit (created_date) values (?)", Statement.RETURN_GENERATED_KEYS);){
                st.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                st.executeUpdate();
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getString(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException("Could not create new data");
        }

    }


}