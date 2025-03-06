package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;

public interface WorkOrderCommandService {
    String handle(CreateWorkOrderCommand command);
}
