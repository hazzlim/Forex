package util;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationHelper {

    private Properties programConfig = new Properties();

    public ConfigurationHelper() {
        init("config.properties", "extra.properties", "log4j.properties");
    }

    public ConfigurationHelper(String configFile) {
        init(configFile, "extra.properties", "log4j.properties");
    }

    public ConfigurationHelper(String configFile, String extraPropertiesFile) {
        init(configFile, extraPropertiesFile, "log4j.properties");
    }

    public ConfigurationHelper(String configFile, String extraPropertiesFile, String logFileProperties) {
        init(configFile, extraPropertiesFile, logFileProperties);
    }

    public void init(String configFile, String extraPropertiesFile, String logFileProperties) {
        try {
            programConfig.load(new FileInputStream(new File(configFile)));
            programConfig.load(new FileInputStream(new File(extraPropertiesFile)));
            PropertyConfigurator.configure(logFileProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getProperty(String key) {
        return programConfig.getProperty(key);
    }
}
