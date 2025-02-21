package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateServiceOrderResource;

public class CreateServiceOrderCommandFromResourceAssembler {
    public static CreateWorkOrderCommand toCommandFromResource(CreateServiceOrderResource resource) {
        return CreateWorkOrderCommand.builder()
                .build();
    }
}
