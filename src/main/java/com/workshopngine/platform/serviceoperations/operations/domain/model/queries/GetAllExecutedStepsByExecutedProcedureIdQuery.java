package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllExecutedStepsByExecutedProcedureIdQuery(String serviceOrderId, String executedProcedureId) {
    public GetAllExecutedStepsByExecutedProcedureIdQuery {
        if (serviceOrderId == null || serviceOrderId.isBlank()) {
            throw new IllegalArgumentException("Service Order ID must not be null or blank");
        }
        if (executedProcedureId == null || executedProcedureId.isBlank()) {
            throw new IllegalArgumentException("Executed Procedure ID must not be null or blank");
        }
    }
}