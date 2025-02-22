package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;

import java.util.Optional;

public interface ServiceOrderCommandService {
    String handle(CreateServiceOrderCommand command);
    Optional<ExecutedProcedure> handle(CreateExecutedProcedureCommand command);
}
