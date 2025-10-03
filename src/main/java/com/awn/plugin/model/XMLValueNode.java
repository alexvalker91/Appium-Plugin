package com.awn.plugin.model;

public class XMLValueNode {
    private String label;
    private String value;
    private String variable;

    public XMLValueNode() {}

    public XMLValueNode(String label, String value, String variable) {
        this.label = label;
        this.value = value;
        this.variable = variable;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getVariable() {
        return variable;
    }
}
