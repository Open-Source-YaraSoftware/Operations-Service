package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

public record CreateDiagnosticFindingResource(
        String description,
        String severity,
        Integer estimatedRepairCost
) {
}
