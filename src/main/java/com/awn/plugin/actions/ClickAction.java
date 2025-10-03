package com.awn.plugin.actions;

import com.awn.plugin.model.StepData;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ClickAction extends BaseAction {
    @Override
    public String getName() {
        return "StepAction1"; // example mapping
    }

    @Override
    public void execute(AppiumDriver driver, StepData stepData) {
        WebElement el = findElement(driver, stepData);
        el.click();
    }
}
