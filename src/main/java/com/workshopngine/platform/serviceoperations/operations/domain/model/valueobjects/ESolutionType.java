package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum ESolutionType {
    IMMEDIATE,
    OPTIONAL,
    PREVENTIVE;

    public static ESolutionType fromString(String value) {
        for (ESolutionType solutionType : ESolutionType.values()) {
            if (solutionType.name().equalsIgnoreCase(value)) {
                return solutionType;
            }
        }
        throw new IllegalArgumentException("Invalid solution type: " + value);
    }
}
