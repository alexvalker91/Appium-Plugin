package com.awn.plugin.model;

import java.util.*;

public class StepData {
    private final Map<String, List<XMLValueNode>> meaningTypeToNodes = new HashMap<>();

    public void addNode(String meaningType, XMLValueNode node) {
        meaningTypeToNodes.computeIfAbsent(meaningType, k -> new ArrayList<>()).add(node);
    }

    public XMLValueNode getNodeByType(String meaningType) {
        List<XMLValueNode> list = meaningTypeToNodes.get(meaningType);
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    public List<XMLValueNode> getNodesByType(String meaningType) {
        return meaningTypeToNodes.getOrDefault(meaningType, Collections.emptyList());
    }

    public Map<String, List<XMLValueNode>> getAll() {
        return Collections.unmodifiableMap(meaningTypeToNodes);
    }
}
