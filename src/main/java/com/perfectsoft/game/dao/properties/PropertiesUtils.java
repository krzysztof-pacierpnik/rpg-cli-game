package com.perfectsoft.game.dao.properties;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtils {

    private PropertiesUtils() {}

    public static Properties loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(PropertiesUtils.class.getResourceAsStream(path));
        return properties;
    }
}
