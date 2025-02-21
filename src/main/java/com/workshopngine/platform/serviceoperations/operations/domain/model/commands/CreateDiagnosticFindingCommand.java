package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ProposedSolution;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateDiagnosticFindingCommand(
        String diagnosticId,
        String description,
        EFindingSeverity severity,
        ProposedSolution proposedSolution,
        BigDecimal estimatedRepairCost,
        String remarks
) {
}
