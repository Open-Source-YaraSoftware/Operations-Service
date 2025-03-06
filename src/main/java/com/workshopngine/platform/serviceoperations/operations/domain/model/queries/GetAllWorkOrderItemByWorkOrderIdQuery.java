package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetAllWorkOrderItemByWorkOrderIdQuery(
    String workOrderId
) {
    public GetAllWorkOrderItemByWorkOrderIdQuery {
        if (workOrderId == null || workOrderId.isBlank()) {
            throw new IllegalArgumentException("workOrderId cannot be null or empty");
        }
    }
}
