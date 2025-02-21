package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import org.apache.logging.log4j.util.Strings;

@Embeddable
public record ServiceId(String serviceId) {
    public ServiceId() {
        this(Strings.EMPTY);
    }
}
