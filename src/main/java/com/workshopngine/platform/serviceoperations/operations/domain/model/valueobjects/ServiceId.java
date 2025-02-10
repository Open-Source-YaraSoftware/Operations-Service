package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ServiceId(String serviceId) {
    public ServiceId {
        if (serviceId == null || serviceId.isBlank()) {
            throw new IllegalArgumentException("ServiceId cannot be null or empty");
        }
    }
}
