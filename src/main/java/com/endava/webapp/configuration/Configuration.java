package com.endava.webapp.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {
    public String getConfigProperties(String propName) throws IOException {
        String filename = "application.properties";
        InputStream input = getClass().getClassLoader().
                getResourceAsStream(filename);
        if (input == null) {
            return "Sorry, unable to find " + filename;
        }

        Properties properties = new Properties();
        properties.load(new InputStreamReader(input, "UTF-8"));
        return properties.getProperty(propName);
    }
}
