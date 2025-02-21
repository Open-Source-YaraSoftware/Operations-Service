package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;

public interface ServiceOrderCommandService {
    String handle(CreateWorkOrderCommand command);
}
