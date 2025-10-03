package com.awn.plugin.core;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AppiumDriverManager {
    private static final Logger log = LoggerFactory.getLogger(AppiumDriverManager.class);

    private AppiumDriver driver;

    public void start() {
        try {
            String serverUrl = getEnvOrDefault("APPIUM_SERVER_URL", "http://127.0.0.1:4723/");
            String platformName = getEnvOrDefault("APPIUM_PLATFORM_NAME", "Android");
            String deviceName = getEnvOrDefault("APPIUM_DEVICE_NAME", "Android Emulator");
            String appPath = System.getenv("APPIUM_APP");
            String udid = System.getenv("APPIUM_UDID");

            BaseOptions<?> options = new BaseOptions<>()
                    .amend("platformName", platformName)
                    .amend("deviceName", deviceName);
            if (appPath != null && !appPath.isBlank()) {
                options = options.amend("app", appPath);
            }
            if (udid != null && !udid.isBlank()) {
                options = options.amend("udid", udid);
            }

            URL url = new URL(serverUrl);
            driver = new AppiumDriver(url, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getIntEnv("APPIUM_IMPLICIT_WAIT_SEC", 5)));
            log.info("Appium driver started: {} {}", platformName, deviceName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start Appium driver", e);
        }
    }

    public AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not started. Call start() first.");
        }
        return driver;
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
            log.info("Appium driver stopped");
        }
    }

    private static String getEnvOrDefault(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? def : v;
    }

    private static int getIntEnv(String key, int def) {
        try {
            String v = System.getenv(key);
            return v == null ? def : Integer.parseInt(v);
        } catch (Exception e) {
            return def;
        }
    }
}
