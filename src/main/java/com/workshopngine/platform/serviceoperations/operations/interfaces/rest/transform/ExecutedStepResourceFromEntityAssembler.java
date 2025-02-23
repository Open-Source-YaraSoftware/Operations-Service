package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedStep;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedStepResource;

public class ExecutedStepResourceFromEntityAssembler {
    public static ExecutedStepResource toResourceFromEntity(ExecutedStep entity) {
        return ExecutedStepResource.builder()
                .build(
        );
    }
}
