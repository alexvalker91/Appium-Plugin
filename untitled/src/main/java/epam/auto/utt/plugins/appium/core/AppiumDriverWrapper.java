package epam.auto.utt.plugins.appium.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class AppiumDriverWrapper {

    private static AppiumDriverWrapper instance;
    private AppiumDriver driver;

    private AppiumDriverWrapper() {}

    public static AppiumDriverWrapper getInstance() {
        if (instance == null) {
            synchronized (AppiumDriverWrapper.class) {
                if (instance == null) {
                    instance = new AppiumDriverWrapper();
                }
            }
        }
        return instance;
    }

    public void configure(Map<String, String> configurations, String configurationUrl) throws MalformedURLException {
        if (driver == null) {
            synchronized (this) {
                if (driver == null) {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    for (Map.Entry<String, String> entry : configurations.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        capabilities.setCapability(key, value);
                    }
                    driver = new AndroidDriver(new URL(configurationUrl), capabilities);
                }
            }
        }
    }
    public AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver is not configured. Call configure() first.");
        }
        return driver;
    }
}
