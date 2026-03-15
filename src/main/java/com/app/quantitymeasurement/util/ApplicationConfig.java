package com.app.quantitymeasurement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static ApplicationConfig instance;
    private final Properties properties = new Properties();

    private ApplicationConfig() {
        loadProperties();
    }

    public static ApplicationConfig getInstance() {
        if (instance == null)
            instance = new ApplicationConfig();
        return instance;
    }

    private void loadProperties() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                properties.load(is);
                logger.info("application.properties loaded successfully");
            } else {
                logger.warn("application.properties not found, using defaults");
            }
        } catch (IOException e) {
            logger.error("Failed to load application.properties", e);
        }
    }

    public String get(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public String getRepositoryType() {
        return get("repository.type", "cache");
    }

    public String getDbUrl()      { return get("db.url",      "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1"); }
    public String getDbUsername() { return get("db.username", "sa"); }
    public String getDbPassword() { return get("db.password", ""); }
    public int    getPoolSize()   { return Integer.parseInt(get("db.pool.size", "5")); }
}
