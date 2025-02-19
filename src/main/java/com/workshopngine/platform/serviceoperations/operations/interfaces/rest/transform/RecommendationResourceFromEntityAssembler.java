package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.RecommendationResource;

public class RecommendationResourceFromEntityAssembler {
    public static RecommendationResource toResourceFromEntity(Recommendation entity){
        return new RecommendationResource(
                entity.getId(),
                entity.getContent()
        );
    }
}
