package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllExecutedProceduresByServiceOrderIdQuery(String serviceOrderId) {
    public GetAllExecutedProceduresByServiceOrderIdQuery {
        if (serviceOrderId == null || serviceOrderId.isBlank()) {
            throw new IllegalArgumentException("Service Order ID cannot be null or empty");
        }
    }
}
