package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

public record DiagnosticResource(
        String id,
        String workshopId,
        String vehicleId,
        String mechanicId,
        String reasonForDiagnostic,
        String expectedOutcome,
        String diagnosticProcedure,
        String status
) {
}
