package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
public class TimeInterval {
    @NotNull
    private LocalDateTime start;

    private LocalDateTime end;

    private Duration duration;

    public TimeInterval() {
        this.start = LocalDateTime.now();
    }
}
