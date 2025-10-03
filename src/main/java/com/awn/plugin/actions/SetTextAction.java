package com.awn.plugin.actions;

import com.awn.plugin.model.StepData;
import com.awn.plugin.model.XMLValueNode;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class SetTextAction extends BaseAction {
    @Override
    public String getName() {
        return "SetText";
    }

    @Override
    public void execute(AppiumDriver driver, StepData stepData) {
        WebElement el = findElement(driver, stepData);
        XMLValueNode textNode = stepData.getNodeByType("text");
        if (textNode == null || textNode.getValue() == null) {
            throw new IllegalArgumentException("Missing 'text' meaning with value for SetText");
        }
        el.clear();
        el.sendKeys(textNode.getValue());
    }
}
