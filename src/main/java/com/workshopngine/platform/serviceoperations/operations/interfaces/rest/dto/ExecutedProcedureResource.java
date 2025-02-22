package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.time.Duration;

@Builder
public record ExecutedProcedureResource(
        String id,
        String standardProcedureId,
        String name,
        String description,
        Duration estimatedTime,
        Duration actualTimeSpent,
        String outcome,
        String status
) {
}
