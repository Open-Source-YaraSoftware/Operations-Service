package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum ERequestType {
    IMMEDIATE,
    SCHEDULED;

    public static ERequestType fromString(String requestType) {
        return switch (requestType) {
            case "IMMEDIATE" -> IMMEDIATE;
            case "SCHEDULED" -> SCHEDULED;
            default -> throw new IllegalArgumentException("Invalid request type: " + requestType);
        };
    }
}
