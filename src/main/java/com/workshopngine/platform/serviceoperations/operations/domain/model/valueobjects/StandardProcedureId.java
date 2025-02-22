package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record StandardProcedureId(String standardProcedureId) {
    public StandardProcedureId {
        if (standardProcedureId.isBlank()) {
            throw new IllegalArgumentException("Standard procedure id cannot be blank");
        }
    }
}
