package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import java.math.BigDecimal;

public record DiagnosticFindingResource(
        String id,
        String description,
        String severity,
        String solutionDescription,
        String solutionType,
        BigDecimal estimatedRepairCost,
        String status,
        String remarks
) {
}
