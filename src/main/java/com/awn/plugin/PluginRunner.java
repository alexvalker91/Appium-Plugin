package com.awn.plugin;

import com.awn.plugin.core.ActionFactory;
import com.awn.plugin.core.AppiumDriverManager;
import com.awn.plugin.model.AwnStep;
import com.awn.plugin.parser.AwnXmlParser;
import com.awn.plugin.actions.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.nio.file.Path;

public class PluginRunner {
    private static final Logger log = LoggerFactory.getLogger(PluginRunner.class);

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java -jar appium-xml-plugin.jar <path-to-xml>");
            System.exit(1);
        }
        Path xmlPath = Path.of(args[0]);
        AppiumDriverManager driverManager = new AppiumDriverManager();
        driverManager.start();
        try (FileInputStream fis = new FileInputStream(xmlPath.toFile())) {
            AwnXmlParser parser = new AwnXmlParser();
            AwnStep[] steps = parser.parse(fis);
            ActionFactory factory = new ActionFactory();
            for (AwnStep step : steps) {
                if (!step.isExecute()) {
                    log.info("Skipping step {} ({})", step.getId(), step.getActionName());
                    continue;
                }
                BaseAction action = factory.get(step.getActionName());
                log.info("Executing step {} -> {}", step.getId(), action.getName());
                action.execute(driverManager.getDriver(), step.getStepData());
            }
        } finally {
            driverManager.stop();
        }
    }
}
