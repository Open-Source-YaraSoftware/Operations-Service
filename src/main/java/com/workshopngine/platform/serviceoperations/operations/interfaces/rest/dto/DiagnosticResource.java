package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import java.time.LocalDateTime;

public record DiagnosticResource(
        String id,
        String workshopId,
        String vehicleId,
        String mechanicId,
        String diagnosticType,
        String desiredOutcome,
        String details,
        String status,
        LocalDateTime completedAt
) {
}
