package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllExecutedProceduresByServiceOrderIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetExecutedProcedureByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetServiceOrderByIdQuery;

import java.util.Collection;
import java.util.Optional;

public interface ServiceOrderQueryService {
    Optional<ServiceOrder> handle(GetServiceOrderByIdQuery query);
    Optional<ExecutedProcedure> handle(GetExecutedProcedureByIdQuery query);
    Collection<ExecutedProcedure> handle(GetAllExecutedProceduresByServiceOrderIdQuery query);
}
