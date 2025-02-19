package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;

import java.util.Collection;

public record CreateDiagnosticFindingCommand(
        String diagnosticId,
        String description,
        EFindingSeverity severity,
        Integer estimatedRepairCost
) {
}
