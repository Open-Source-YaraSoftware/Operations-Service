package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;

public interface ServiceOrderCommandService {
    String handle(CreateServiceOrderCommand command);
}
