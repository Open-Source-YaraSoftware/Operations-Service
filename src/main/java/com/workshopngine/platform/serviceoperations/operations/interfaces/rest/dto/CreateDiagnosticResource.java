package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreateDiagnosticResource(
        String workshopId,
        String vehicleId,
        String mechanicId,
        String diagnosticType,
        String desiredOutcome,
        String details
) {
}
