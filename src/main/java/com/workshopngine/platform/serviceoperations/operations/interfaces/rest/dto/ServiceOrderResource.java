package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

import java.time.Duration;

@Builder
public record ServiceOrderResource(
        String id,
        String serviceType,
        String workshopId,
        String vehicleId,
        String clientId,
        String assignedMechanicId,
        String workOrderId,
        String status,
        Duration totalTimeSpent
) {
}
