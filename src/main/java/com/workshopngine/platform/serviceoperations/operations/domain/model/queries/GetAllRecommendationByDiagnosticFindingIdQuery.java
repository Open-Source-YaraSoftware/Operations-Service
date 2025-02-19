package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllRecommendationByDiagnosticFindingIdQuery(
        String diagnosticId,
        String diagnosticFindingId
) {
}
