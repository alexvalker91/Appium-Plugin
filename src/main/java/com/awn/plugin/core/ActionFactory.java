package com.awn.plugin.core;

import com.awn.plugin.actions.BaseAction;
import com.awn.plugin.actions.ClickAction;
import com.awn.plugin.actions.SetTextAction;
import com.awn.plugin.actions.WebBrowserNavigation;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private final Map<String, BaseAction> actions = new HashMap<>();

    public ActionFactory() {
        register(new ClickAction());
        register(new SetTextAction());
        register(new WebBrowserNavigation());
    }

    public void register(BaseAction action) {
        actions.put(action.getName(), action);
    }

    public BaseAction get(String name) {
        BaseAction a = actions.get(name);
        if (a == null) {
            throw new IllegalArgumentException("No action registered with name: " + name);
        }
        return a;
    }
}
