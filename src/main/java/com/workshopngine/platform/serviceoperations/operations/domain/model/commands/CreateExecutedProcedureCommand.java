package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardProcedureId;
import lombok.Builder;

import java.time.Duration;

@Builder
public record CreateExecutedProcedureCommand(
        String serviceOrderId,
        StandardProcedureId standardProcedureId,
        String name,
        String description,
        Duration estimatedTime
) {
}
