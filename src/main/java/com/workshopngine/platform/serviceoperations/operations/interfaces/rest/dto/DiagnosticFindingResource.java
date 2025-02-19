package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

public record DiagnosticFindingResource(
        String id,
        String diagnosticId,
        String description,
        String severity,
        Integer estimatedRepairCost
) {
}
