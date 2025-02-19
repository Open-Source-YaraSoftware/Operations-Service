package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetDiagnosticByIdQuery(String diagnosticId) {
    public GetDiagnosticByIdQuery {
        if (diagnosticId == null || diagnosticId.isBlank()) {
            throw new IllegalArgumentException("Diagnostic ID must not be null or blank");
        }
    }
}
