package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllEvidencesByDiagnosticFindingIdQuery(
        String diagnosticId,
        String diagnosticFindingId
) {
}
