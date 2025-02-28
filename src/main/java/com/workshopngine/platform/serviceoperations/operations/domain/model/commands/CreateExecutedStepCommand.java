package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardStepId;
import lombok.Builder;

import java.time.Duration;

@Builder
public record CreateExecutedStepCommand(
        String serviceOrderId,
        String executedProcedureId,
        StandardStepId standardStepId,
        MechanicId assignedMechanicId,
        Integer stepOrder,
        String name,
        String description,
        Duration estimatedTime,
        Boolean qualityCheckRequired
) {
}
