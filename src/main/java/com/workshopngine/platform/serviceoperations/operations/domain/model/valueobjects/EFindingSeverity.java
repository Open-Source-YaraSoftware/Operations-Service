package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum EFindingSeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW;

    public static EFindingSeverity fromString(String severity) {
        return switch (severity) {
            case "CRITICAL" -> CRITICAL;
            case "HIGH" -> HIGH;
            case "MEDIUM" -> MEDIUM;
            case "LOW" -> LOW;
            default -> throw new IllegalArgumentException("Invalid severity: " + severity);
        };
    }
}
