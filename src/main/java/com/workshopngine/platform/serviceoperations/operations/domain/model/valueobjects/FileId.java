package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FileId(String fileId) {
    public FileId {
        if (fileId == null) {
            throw new IllegalArgumentException("FileId cannot be null");
        }
    }
}
