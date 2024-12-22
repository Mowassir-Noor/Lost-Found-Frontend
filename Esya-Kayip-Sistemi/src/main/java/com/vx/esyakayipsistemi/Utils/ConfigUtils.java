package com.vx.esyakayipsistemi.Utils;

import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ConfigUtils {

    //config.properties deki degiskenlere erisebilmek icin bir fonksiyon
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtils.class.getResourceAsStream("/config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new InvalidPropertiesFormatException("config.properties file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key, "Unknown Key not found ");
    }


}