package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
public class Cost {
    private BigDecimal totalCost;

    private String currency;

    public Cost() {
        this.totalCost = BigDecimal.ZERO;
        this.currency = "PEN";
    }
}
