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
public class CostEstimate {
    private BigDecimal amount;

    private String currency;

    private ECostStatus costStatus;

    public CostEstimate() {
        this.amount = BigDecimal.ZERO;
        this.currency = "PEN";
        this.costStatus = ECostStatus.ESTIMATED;
    }
}
