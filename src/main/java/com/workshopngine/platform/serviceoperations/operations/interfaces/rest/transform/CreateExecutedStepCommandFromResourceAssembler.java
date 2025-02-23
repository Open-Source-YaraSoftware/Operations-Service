package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedStepCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedStepResource;

public class CreateExecutedStepCommandFromResourceAssembler {
    public static CreateExecutedStepCommand toCommandFromResource(String serviceOrderId, String executedProcedureId, CreateExecutedStepResource resource) {
        return CreateExecutedStepCommand.builder()
                .build();
    }
}
