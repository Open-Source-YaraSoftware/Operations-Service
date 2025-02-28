package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record StandardStepId(String standardStepId) {
    public StandardStepId() {
        this(null);
    }
}
