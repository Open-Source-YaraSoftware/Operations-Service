package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record WorkOrderId(String workOrderId) {
    public WorkOrderId {
        if (workOrderId.isBlank()) {
            throw new IllegalArgumentException("WorkOrderId cannot be blank");
        }
    }
}
