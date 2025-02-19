package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateRecommendationResource;

public class CreateRecommendationCommandFromResourceAssembler {
    public static CreateRecommendationCommand toCommandFromResource(
            String diagnosticId,
            String findingId,
            CreateRecommendationResource resource)
    {
        return new CreateRecommendationCommand(
                diagnosticId,
                findingId,
                resource.content()
        );
    }
}
