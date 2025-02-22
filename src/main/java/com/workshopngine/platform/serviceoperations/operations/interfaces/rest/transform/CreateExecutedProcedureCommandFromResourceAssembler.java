package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedProcedureResource;

public class CreateExecutedProcedureCommandFromResourceAssembler {
    public static CreateExecutedProcedureCommand toCommandFromResource(CreateExecutedProcedureResource resource) {
        return CreateExecutedProcedureCommand.builder()
                .build();
    }
}
