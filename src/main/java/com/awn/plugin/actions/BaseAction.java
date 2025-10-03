package com.awn.plugin.actions;

import com.awn.plugin.model.StepData;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class BaseAction {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public abstract String getName();

    public abstract void execute(AppiumDriver driver, StepData stepData);

    protected WebElement findElement(AppiumDriver driver, StepData data) {
        // Supports meaning types: id, xpath, accessibility_id, css, className, android_uiautomator, ios_predicate
        By locator = buildLocator(data);
        return driver.findElement(locator);
    }

    protected List<WebElement> findElements(AppiumDriver driver, StepData data) {
        By locator = buildLocator(data);
        return driver.findElements(locator);
    }

    protected By buildLocator(StepData data) {
        String[] supported = {"id", "xpath", "accessibility_id", "css", "className", "android_uiautomator", "ios_predicate"};
        for (String key : supported) {
            var node = data.getNodeByType(key);
            if (node != null && node.getValue() != null && !node.getValue().isBlank()) {
                return switch (key) {
                    case "id" -> By.id(node.getValue());
                    case "xpath" -> By.xpath(node.getValue());
                    case "accessibility_id" -> AppiumBy.accessibilityId(node.getValue());
                    case "css" -> By.cssSelector(node.getValue());
                    case "className" -> By.className(node.getValue());
                    case "android_uiautomator" -> AppiumBy.androidUIAutomator(node.getValue());
                    case "ios_predicate" -> AppiumBy.iOSNsPredicateString(node.getValue());
                    default -> throw new IllegalArgumentException("Unsupported locator type: " + key);
                };
            }
        }
        throw new IllegalArgumentException("No supported locator provided in Step_Data");
    }

    protected Duration getDurationOrDefault(StepData data, String type, int defaultMs) {
        var node = data.getNodeByType(type);
        if (node == null || node.getValue() == null) return Duration.ofMillis(defaultMs);
        try {
            return Duration.ofMillis(Integer.parseInt(node.getValue()));
        } catch (NumberFormatException e) {
            return Duration.ofMillis(defaultMs);
        }
    }
}
