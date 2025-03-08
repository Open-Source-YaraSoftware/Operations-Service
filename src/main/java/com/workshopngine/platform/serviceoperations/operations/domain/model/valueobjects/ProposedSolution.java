package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProposedSolution(
        String solutionDescription,
        ESolutionType solutionType
) {
}
