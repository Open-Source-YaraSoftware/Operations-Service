package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum EServiceType {
    DIAGNOSTIC,
    REPAIR,
    MAINTENANCE;

    public static EServiceType fromString(String serviceType) {
        return switch (serviceType) {
            case "DIAGNOSTIC" -> DIAGNOSTIC;
            case "REPAIR" -> REPAIR;
            case "MAINTENANCE" -> MAINTENANCE;
            default -> throw new IllegalArgumentException("Invalid service type: " + serviceType);
        };
    }
}
