package epam.auto.utt.plugins.appium.actions;

import epam.auto.utt.actions.BaseAction;
import epam.auto.utt.parser.xml.XMLValueNode;
import epam.auto.utt.plugins.appium.core.AppiumDriverWrapper;
import epam.auto.utt.plugins.appium.utils.LocatorInfoFactory;
import epam.auto.utt.run.result.Result;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementAction extends BaseAction {

    @Override
    public Result runAction() {
        AppiumDriver driver = AppiumDriverWrapper.getInstance().getDriver();
        XMLValueNode locatorNode = stepData.getNodeByType("web_element_locator");
        Map<String, String> locatorNodeMap = LocatorInfoFactory.parseDefaultLocatorNode(locatorNode);
        List<WebElement> elements = LocatorInfoFactory.parseLocator(driver, locatorNodeMap);
        makeActions(elements, stepData.getNodesByType("web_element_action_multiple"), this::getNthNumber, driver);
        return passedResult;
    }

    private void makeActions(List<WebElement> element, List<XMLValueNode> nodes, Function<XMLValueNode, Integer> getNth, AppiumDriver driver) {
        nodes.forEach(node -> makeNthElementAction(element, getNth.apply(node), node, driver));
    }

    private void makeNthElementAction(List<WebElement> elements, Integer nth, XMLValueNode node, AppiumDriver driver) {
        String action = node.getVariable();

        switch (action.toUpperCase()) {
            case "CLICK":
                elements.get(nth).click();
                break;
            case "WAIT_UNTIL":
                String condition = node.getLabel();
                ExpectedCondition<?> expectedCondition = getCondition(condition, elements.get(nth));
                new WebDriverWait(driver, Duration.ofSeconds(LocatorInfoFactory.parseInt(node.getValue())))
                        .until(expectedCondition);
                break;
            default:
                throw new IllegalArgumentException("Action is not supported: " + action);
        }
    }

    private ExpectedCondition<?> getCondition(String condition, WebElement element) {
        switch (condition.toUpperCase()) {
            case "VISIBLE":
                return ExpectedConditions.visibilityOf(element);
            case "CLICKABLE":
                return ExpectedConditions.elementToBeClickable(element);
            case "ENABLED" :
                return driver -> element.isEnabled();
            default:
                throw new IllegalArgumentException("Condition is not supported: " + condition);
        }
    }

    private Integer getNthNumber(XMLValueNode node) {
        String nth = node.getFeature();
        if (!StringUtils.isNumeric(nth)) {
            throw new IllegalArgumentException("Expected number value for element number. Acquired value: [" + nth + "]");
        }
        return Integer.valueOf(nth);
    }
}
