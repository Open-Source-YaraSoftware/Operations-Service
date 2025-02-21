package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreateServiceOrderResource(
        String serviceType,
        String workshopId,
        String vehicleId,
        String clientId,
        String assignedMechanicId,
        String workOrderId
) {
}
