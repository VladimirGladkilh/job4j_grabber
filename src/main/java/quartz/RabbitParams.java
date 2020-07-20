package quartz;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class RabbitParams {
    private static Connection cn;
    public static int workInterval = 0;

    public static int getWorkInterval() {
        return workInterval;
    }

    public RabbitParams() {
        init();
    }

    public static Connection getConnection() {
        return cn;
    }

    public void init() {
        try (InputStream in = RabbitParams.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            workInterval = Integer.parseInt(config.getOrDefault("rabbit.interval", "10").toString());
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password"));

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
