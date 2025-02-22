package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardProcedureId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedProcedureResource;

public class CreateExecutedProcedureCommandFromResourceAssembler {
    public static CreateExecutedProcedureCommand toCommandFromResource(String serviceOrderId, CreateExecutedProcedureResource resource) {
        return CreateExecutedProcedureCommand.builder()
                .serviceOrderId(serviceOrderId)
                .standardProcedureId(new StandardProcedureId(resource.standardProcedureId()))
                .name(resource.name())
                .description(resource.description())
                .estimatedTime(resource.estimatedTime())
                .build();
    }
}
