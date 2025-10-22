package epam.auto.utt.plugins.appium.utils;

import epam.auto.utt.parser.xml.XMLValueNode;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LocatorInfoFactory {

    private LocatorInfoFactory() {
    }

    public static List<WebElement> parseLocator(AppiumDriver driver, Map<String, String> node) {
        String type = Optional.ofNullable(node.get("variable"))
                .orElseThrow(() -> new IllegalArgumentException("Can't detect locator type"));
        switch (type.toUpperCase()) {
            case "ID":
                return driver.findElements(By.id(node.getOrDefault("value", "")));
            case "XPATH":
                return driver.findElements(By.xpath(node.getOrDefault("value", "")));
            case "NAME":
                return driver.findElements(By.name(node.getOrDefault("value", "")));
            case "CLASS_NAME":
                return driver.findElements(By.className(node.getOrDefault("value", "")));
            case "ACCESSIBILITY_ID": // TODO, не рекомендуется использовать, в последних версиях Appium
            case "ANDROIDUIAUTOMATOR": // TODO, не рекомендуется использовать, в последних версиях Appium
            default:
                throw new IllegalArgumentException("Unknown locator type: " + type);
        }
    }

    public static Map<String, String> parseDefaultLocatorNode(XMLValueNode node) {
        Map<String, String> mapNode = new HashMap<>();
        Optional.ofNullable(node).ifPresent(n -> {
            mapNode.put("value", n.getValue());
            mapNode.put("lbl", n.getLabel());
            mapNode.put("variable", n.getVariable());
        });

        return mapNode;
    }

    public static int parseInt(String str) {
        return Integer.parseInt(str);
    }
}
