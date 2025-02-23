package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.time.Duration;

@Builder
public record ExecutedStepResource(
        String id,
        String standardStepId,
        String assignedMechanicId,
        Integer stepOrder,
        String name,
        String description,
        Duration estimatedTime,
        Duration actualTimeSpent,
        Boolean qualityCheckRequired,
        String qualityCheckStatus,
        String status
) {
}
