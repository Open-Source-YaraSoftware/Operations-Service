package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiagnosticDetails(
        String reasonForDiagnostic,
        String expectedOutcome,
        String diagnosticProcedure
) {
    public DiagnosticDetails {
        if (reasonForDiagnostic == null || reasonForDiagnostic.isBlank()) {
            throw new IllegalArgumentException("reasonForDiagnostic cannot be null or empty");
        }
    }
}
