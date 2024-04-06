package utils.runner;

import utils.LoggerUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigProperties {
    private static Properties properties = initProperties();

    public static final Map<String, String> ENVIRONMENT_CHROMIUM = selfEnvironment("browserName1", "isHeadless1", "slowMo1");
    public static final Map<String, String> ENVIRONMENT_FIREFOX = selfEnvironment("browserName2", "isHeadless2", "slowMo2");
    public static final Map<String, String> ENVIRONMENT_WEBKIT = selfEnvironment("browserName3", "isHeadless3", "slowMo3");

    public static final String URL = properties.getProperty("url");

    private static Properties initProperties() {
        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
        }  catch (IOException e) { //FileNotFoundException
            LoggerUtils.logError("ERROR: Properties file NOT found OR the file is empty OR the file is corrupted");
        } catch (Exception e) {
            LoggerUtils.logFatal("FATAL: Properties file NOT found");
            System.exit(1);
        }

        return properties;
    }

    private static Map<String, String> selfEnvironment(String browser, String isHeadless, String slowMo) {
        Map<String, String> environment = new HashMap<>();

        if(properties != null && !properties.isEmpty()
                && properties.containsKey(browser) && !properties.getProperty(browser).trim().isEmpty()
                && properties.containsKey(isHeadless) && !properties.getProperty(isHeadless).trim().isEmpty()
                && properties.containsKey(slowMo) && !properties.getProperty(slowMo).trim().isEmpty()
        ) {
            environment.put("browser", properties.getProperty(browser).trim());
            environment.put("isHeadless", properties.getProperty(isHeadless).trim());
            environment.put("slowMo", properties.getProperty(slowMo).trim());
        } else {
            LoggerUtils.logWarning("WARNING: Set DEFAULT environment.");

            //default environment
            environment.put("browser", "chromium");
            environment.put("isHeadless", "true");
            environment.put("slowMo", "0");
        }

        return environment;
    }
}
