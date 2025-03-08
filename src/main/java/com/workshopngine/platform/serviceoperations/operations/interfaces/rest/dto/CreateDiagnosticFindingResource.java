package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateDiagnosticFindingResource(
        String description,
        String severity,
        String solutionDescription,
        String solutionType,
        BigDecimal estimatedRepairCost,
        String remarks
) {
}
