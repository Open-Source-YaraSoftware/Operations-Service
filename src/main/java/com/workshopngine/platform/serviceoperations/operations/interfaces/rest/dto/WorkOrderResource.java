package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

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
        BigDecimal totalCost,
        String currency,
        LocalDateTime start,
        LocalDateTime end,
        Duration duration
) {
}
