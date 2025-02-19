package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

public record CreateRecommendationCommand(
        String diagnosticId,
        String diagnosticFindingId,
        String content
) {
}
