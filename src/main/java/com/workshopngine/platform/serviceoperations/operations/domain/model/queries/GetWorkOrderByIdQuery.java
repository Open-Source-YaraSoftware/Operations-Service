package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetWorkOrderByIdQuery(String workOrderId) {
    public GetWorkOrderByIdQuery {
        if (workOrderId == null || workOrderId.isBlank()) {
            throw new IllegalArgumentException("Work Order ID must not be null or blank");
        }
    }
}
