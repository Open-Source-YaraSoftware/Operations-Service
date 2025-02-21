package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import java.math.BigDecimal;

public record CreateDiagnosticFindingResource(
        String description,
        String severity,
        String solutionDescription,
        String solutionType,
        BigDecimal estimatedRepairCost,
        String remarks
) {
}
