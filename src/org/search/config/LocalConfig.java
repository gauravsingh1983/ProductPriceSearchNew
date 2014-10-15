package org.search.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class LocalConfig {
    public static final String PROP_FILE = "config.properties";
    private static Properties props = new Properties();
    
    private static final Logger LOGGER = Logger.getLogger(LocalConfig.class);
    
    static {
        InputStream in = LocalConfig.class.getClassLoader().getResourceAsStream(PROP_FILE);
        try {
            if (in != null) {
                props.load(in);
                in.close();
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load " + PROP_FILE, e);
        }
    }

    public static Properties getProperties() {
        return props;
    }

    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        }
        return getProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key, int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse property: " + key + ", using default value: " + defaultValue);
            value = defaultValue;
        }
        return value;
    }
    
    public static void main(String[] args) {
        System.out.println(Integer.parseInt(LocalConfig.getProperty("download.retry.count")));
        System.out.println(LocalConfig.getProperty("download.connection.timeout"));
        System.out.println(LocalConfig.getProperty("db.schema"));
    }
}
