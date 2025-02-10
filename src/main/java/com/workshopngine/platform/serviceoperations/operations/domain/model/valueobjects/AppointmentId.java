package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AppointmentId(String appointmentId) {
    public AppointmentId {
        if (appointmentId == null || appointmentId.isBlank()) {
            throw new IllegalArgumentException("AppointmentId cannot be null or empty");
        }
    }
}
