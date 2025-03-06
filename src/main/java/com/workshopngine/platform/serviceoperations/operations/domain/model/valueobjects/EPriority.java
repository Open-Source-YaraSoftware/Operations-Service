package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum EPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;

    public static EPriority fromString(String priority) {
        return switch (priority) {
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            case "CRITICAL" -> CRITICAL;
            default -> throw new IllegalArgumentException("Invalid priority: " + priority);
        };
    }
}
