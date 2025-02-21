package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetServiceOrderByIdQuery;

import java.util.Optional;

public interface ServiceOrderQueryService {
    Optional<ServiceOrder> handle(GetServiceOrderByIdQuery query);
}
