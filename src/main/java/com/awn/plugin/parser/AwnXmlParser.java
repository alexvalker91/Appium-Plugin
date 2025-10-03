package com.awn.plugin.parser;

import com.awn.plugin.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class AwnXmlParser {
    public AwnStep[] parse(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            NodeList awnSteps = doc.getElementsByTagName("Awn_Step");
            AwnStep[] steps = new AwnStep[awnSteps.getLength()];
            for (int i = 0; i < awnSteps.getLength(); i++) {
                Element stepEl = (Element) awnSteps.item(i);
                String id = stepEl.getAttribute("id");
                Element actionEl = (Element) stepEl.getElementsByTagName("Step_Action").item(0);
                String actionName = actionEl.getAttribute("action");
                boolean execute = true;
                NodeList execList = stepEl.getElementsByTagName("Step_Execute");
                if (execList != null && execList.getLength() > 0) {
                    Element execEl = (Element) execList.item(0);
                    String execStr = execEl.getAttribute("Execute");
                    if (execStr != null && !execStr.isEmpty()) {
                        execute = Boolean.parseBoolean(execStr);
                    }
                }
                StepData stepData = new StepData();
                NodeList meaningNodes = stepEl.getElementsByTagName("Meaning");
                for (int j = 0; j < meaningNodes.getLength(); j++) {
                    Element meaningEl = (Element) meaningNodes.item(j);
                    String type = meaningEl.getAttribute("meaning_type");
                    String lbl = getTextContent(meaningEl, "lbl");
                    String value = getTextContent(meaningEl, "value");
                    String variable = getTextContent(meaningEl, "variable");
                    stepData.addNode(type, new XMLValueNode(lbl, value, variable));
                }
                steps[i] = new AwnStep(id, actionName, stepData, execute);
            }
            return steps;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    private static String getTextContent(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list == null || list.getLength() == 0) return null;
        Node node = list.item(0);
        return node.getTextContent();
    }
}
