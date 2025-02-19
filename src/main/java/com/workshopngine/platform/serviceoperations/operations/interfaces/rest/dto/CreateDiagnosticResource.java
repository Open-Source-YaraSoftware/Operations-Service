package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

public record CreateDiagnosticResource(
        String workshopId,
        String vehicleId,
        String mechanicId,
        String reasonForDiagnostic,
        String expectedOutcome,
        String diagnosticProcedure
) {
}
