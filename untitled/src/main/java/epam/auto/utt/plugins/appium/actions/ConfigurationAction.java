package epam.auto.utt.plugins.appium.actions;

import epam.auto.utt.actions.BaseAction;
import epam.auto.utt.parser.xml.XMLValueNode;
import epam.auto.utt.plugins.appium.core.AppiumDriverWrapper;
import epam.auto.utt.run.result.Result;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationAction extends BaseAction {

    private final Logger logger = LogManager.getLogger(ConfigurationAction.class);

    @Override
    public Result runAction() {
        try {
            Map<String, String> configurations = getConfigurationFromStep();
            String configurationUrl = getConfigurationUrlFromStep();
            AppiumDriverWrapper driverWrapper = AppiumDriverWrapper.getInstance();
            driverWrapper.configure(configurations, configurationUrl);
            AppiumDriver driver = driverWrapper.getDriver();
            // TODO Добавить проверку ожидания, что все хорошо или что такое, для 'driver'
            return passedResult;
        } catch (MalformedURLException e) {
            logger.error("Error during something: ", e);
            return Result.newFailed();
        }
    }

    private Map<String, String> getConfigurationFromStep() {
        Map<String, String> map = new HashMap<>();
        List<XMLValueNode> nodes = stepData.getNodes();
        for (XMLValueNode node : nodes) {
            String type = node.getValueType();
            String label = node.getLabel();
            logger.info("meaning_type: {}, label:{}", type, label);
            if(type.equals("url")) continue;
            map.put(type, label); // {"appium:automationName" : "UIAutomator2", "appium:platformVersion" : "13", "appium:deviceName" : "samsung SM-A515F", "platformName" : "Android", "appium:app" : "C:/Users/AliaksandrKreyer/Desktop/my/repositories/Appium-Plugin/untitled/src/test/resources/apk/quiz.apk"}
        }
        return map;
    }

    private String getConfigurationUrlFromStep() {
        List<XMLValueNode> nodes = stepData.getNodes();
        for (XMLValueNode node : nodes) {
            String type = node.getValueType();
            String label = node.getLabel();
            logger.info("meaning_type: {}, label:{}", type, label);
            if(type.equals("url")) return label;
        }
        throw new IllegalArgumentException("Not found url");
    }
}
