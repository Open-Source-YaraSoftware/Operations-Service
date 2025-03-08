package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllAttachmentByDiagnosticFindingIdQuery(
        String diagnosticId,
        String diagnosticFindingId
) {
}
