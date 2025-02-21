package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreateWorkOrderResource(
        String clientId,
        String vehicleId,
        String appointmentId,
        String workshopId,
        String mechanicAssignedId,
        String priority,
        String requestType,
        String serviceType
) {
}
