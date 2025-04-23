package org.AGa.rsService;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream in = ConfigurationLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in != null) {
                properties.load(in);
            } else {
                System.err.println("config.properties not found in classpath!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}