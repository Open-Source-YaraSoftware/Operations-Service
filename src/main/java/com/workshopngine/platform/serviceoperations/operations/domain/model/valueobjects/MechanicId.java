package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record MechanicId(String mechanicId) {
    public MechanicId {
        if (mechanicId == null || mechanicId.isBlank()) {
            throw new IllegalArgumentException("MechanicId cannot be null or empty");
        }
    }
}
