package com.awn.plugin.model;

public class AwnStep {
    private final String id;
    private final String actionName;
    private final StepData stepData;
    private final boolean execute;

    public AwnStep(String id, String actionName, StepData stepData, boolean execute) {
        this.id = id;
        this.actionName = actionName;
        this.stepData = stepData;
        this.execute = execute;
    }

    public String getId() {
        return id;
    }

    public String getActionName() {
        return actionName;
    }

    public StepData getStepData() {
        return stepData;
    }

    public boolean isExecute() {
        return execute;
    }
}
