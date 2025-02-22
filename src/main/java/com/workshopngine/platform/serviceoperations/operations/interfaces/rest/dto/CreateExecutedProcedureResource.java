package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.time.Duration;

@Builder
public record CreateExecutedProcedureResource(
        String standardProcedureId,
        String name,
        String description,
        Duration estimatedTime
) {
}
