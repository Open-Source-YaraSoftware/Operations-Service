package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import java.math.BigDecimal;

public record WorkOrderResource(
        String id,
        String clientId,
        String vehicleId,
        String appointmentId,
        String workshopId,
        String mechanicAssignedId,
        String status,
        String priority,
        String requestType,
        BigDecimal amount,
        String currency,
        String costStatus
) {
}
