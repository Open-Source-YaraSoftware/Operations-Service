package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.time.Duration;

@Builder
public record CreateExecutedStepResource(
        String standardStepId,
        String assignedMechanicId,
        Integer stepOrder,
        String name,
        String description,
        Duration estimatedTime,
        Boolean qualityCheckRequired
) {
}
