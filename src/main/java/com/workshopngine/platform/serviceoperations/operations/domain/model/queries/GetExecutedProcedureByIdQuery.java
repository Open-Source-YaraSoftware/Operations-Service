package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetExecutedProcedureByIdQuery(String serviceOrderId, String executedProcedureId) {
    public GetExecutedProcedureByIdQuery {
        if (serviceOrderId == null || serviceOrderId.isBlank()) {
            throw new IllegalArgumentException("Service Order ID cannot be null or empty");
        }
        if (executedProcedureId == null || executedProcedureId.isBlank()) {
            throw new IllegalArgumentException("Executed Procedure ID cannot be null or empty");
        }
    }
}
