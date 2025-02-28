package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedStepCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardStepId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedStepResource;

public class CreateExecutedStepCommandFromResourceAssembler {
    public static CreateExecutedStepCommand toCommandFromResource(String serviceOrderId, String executedProcedureId, CreateExecutedStepResource resource) {
        return CreateExecutedStepCommand.builder()
                .serviceOrderId(serviceOrderId)
                .executedProcedureId(executedProcedureId)
                .standardStepId(new StandardStepId(resource.standardStepId()))
                .assignedMechanicId(new MechanicId(resource.assignedMechanicId()))
                .stepOrder(resource.stepOrder())
                .name(resource.name())
                .description(resource.description())
                .estimatedTime(resource.estimatedTime())
                .qualityCheckRequired(resource.qualityCheckRequired())
                .build();
    }
}
