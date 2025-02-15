package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetWorkOrderByIdQuery;

import java.util.Optional;

public interface WorkOrderQueryService {
    Optional<WorkOrder> handle(GetWorkOrderByIdQuery query);
}
