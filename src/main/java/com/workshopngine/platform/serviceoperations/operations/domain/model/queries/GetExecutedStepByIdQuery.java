package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetExecutedStepByIdQuery(String serviceOrderId, String executedProcedureId, String executedStepId) {
    public GetExecutedStepByIdQuery {
        if (serviceOrderId == null || serviceOrderId.isBlank()) {
            throw new IllegalArgumentException("serviceOrderId cannot be null or blank");
        }
        if (executedProcedureId == null || executedProcedureId.isBlank()) {
            throw new IllegalArgumentException("executedProcedureId cannot be null or blank");
        }
        if (executedStepId == null || executedStepId.isBlank()) {
            throw new IllegalArgumentException("executedStepId cannot be null or blank");
        }
    }
}
