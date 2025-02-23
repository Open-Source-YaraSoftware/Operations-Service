package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedStep;
import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.*;

import java.util.Collection;
import java.util.Optional;

public interface ServiceOrderQueryService {
    Optional<ServiceOrder> handle(GetServiceOrderByIdQuery query);
    Optional<ExecutedProcedure> handle(GetExecutedProcedureByIdQuery query);
    Collection<ExecutedProcedure> handle(GetAllExecutedProceduresByServiceOrderIdQuery query);
    Optional<ExecutedStep> handle(GetExecutedStepByIdQuery query);
    Collection<ExecutedStep> handle(GetAllExecutedStepsByExecutedProcedureIdQuery query);
}
