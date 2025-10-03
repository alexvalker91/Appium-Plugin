package com.awn.plugin.actions;

import com.awn.plugin.model.StepData;
import com.awn.plugin.model.XMLValueNode;
import io.appium.java_client.AppiumDriver;

public class WebBrowserNavigation extends BaseAction {
    @Override
    public String getName() {
        return "WebBrowserNavigation";
    }

    @Override
    public void execute(AppiumDriver driver, StepData stepData) {
        XMLValueNode urlNode = stepData.getNodeByType("url");
        if (urlNode == null || urlNode.getValue() == null || urlNode.getValue().isBlank()) {
            throw new IllegalArgumentException("Missing 'url' meaning for WebBrowserNavigation");
        }
        driver.get(urlNode.getValue());
    }
}
