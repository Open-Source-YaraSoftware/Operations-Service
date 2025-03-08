package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllDiagnosticFindingsByDiagnosticIdQuery(String diagnosticId) {
    public GetAllDiagnosticFindingsByDiagnosticIdQuery {
        if (diagnosticId == null || diagnosticId.isBlank()) {
            throw new IllegalArgumentException("diagnosticId cannot be null or blank");
        }
    }
}
