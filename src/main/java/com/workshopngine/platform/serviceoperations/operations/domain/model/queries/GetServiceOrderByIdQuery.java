package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

public record GetServiceOrderByIdQuery(String serviceOrderId) {
    public GetServiceOrderByIdQuery {
        if (serviceOrderId.isBlank()) {
            throw new IllegalArgumentException("Service Order ID cannot be blank");
        }
    }
}
