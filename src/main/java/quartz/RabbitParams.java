package quartz;

import java.io.InputStream;
import java.util.Properties;

public class RabbitParams {

    public static int workInterval = 0;

    public static int getWorkInterval() {
        return workInterval;
    }

    public void init() {
        try (InputStream in = RabbitParams.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            workInterval = Integer.parseInt(config.getOrDefault("rabbit.interval", "10").toString());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
