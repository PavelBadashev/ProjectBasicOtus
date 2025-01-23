package config;

import java.util.Properties;

public class Utils {
    private static final Properties properties = new Properties();

    public static String getUrl() {
        return properties.getProperty("url", "https://otus.ru/");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }
}
