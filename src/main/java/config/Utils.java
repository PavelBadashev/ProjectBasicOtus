package config;

public class Utils {

    public static String getUrl() {
        return System.getProperty("url", "https://otus.ru/");
    }

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }
}
