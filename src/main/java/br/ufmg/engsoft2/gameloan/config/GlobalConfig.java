package br.ufmg.engsoft2.gameloan.config;

import java.io.InputStream;
import java.util.Properties;

public class GlobalConfig {
    private GlobalConfig() {
        throw new IllegalStateException("Utility class");
    }
    public static void getExternalProperties() {
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String propertiesPath = "config.properties";
        try (InputStream input = classloader.getResourceAsStream(propertiesPath)) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
