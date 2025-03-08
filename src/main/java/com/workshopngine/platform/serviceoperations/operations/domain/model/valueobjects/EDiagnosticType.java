package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum EDiagnosticType {
    PREVENTIVE,
    CORRECTIVE;

    public static EDiagnosticType fromString(String value) {
        for (EDiagnosticType diagnosticType : EDiagnosticType.values()) {
            if (diagnosticType.name().equalsIgnoreCase(value)) {
                return diagnosticType;
            }
        }
        throw new IllegalArgumentException("Invalid diagnostic type: " + value);
    }
}
