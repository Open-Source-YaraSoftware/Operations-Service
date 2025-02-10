package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleId(String vehicleId) {
    public VehicleId {
        if (vehicleId == null || vehicleId.isBlank()) {
            throw new IllegalArgumentException("VehicleId cannot be null or empty");
        }
    }
}
