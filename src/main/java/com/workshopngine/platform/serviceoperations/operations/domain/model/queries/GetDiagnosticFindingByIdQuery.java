package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetDiagnosticFindingByIdQuery(String diagnosticId, String diagnosticFindingId) {
    public GetDiagnosticFindingByIdQuery {
        if (diagnosticId == null || diagnosticId.isBlank()) {
            throw new IllegalArgumentException("Diagnostic ID must not be null or blank");
        }
        if (diagnosticFindingId == null || diagnosticFindingId.isBlank()) {
            throw new IllegalArgumentException("Diagnostic Finding ID must not be null or blank");
        }
    }
}
