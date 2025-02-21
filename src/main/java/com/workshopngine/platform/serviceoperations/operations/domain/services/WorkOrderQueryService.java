package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.WorkOrderItem;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetWorkOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllWorkOrderItemByWorkOrderIdQuery;

import java.util.Collection;
import java.util.Optional;

public interface WorkOrderQueryService {
    Optional<WorkOrder> handle(GetWorkOrderByIdQuery query);
    Collection<WorkOrderItem> handle(GetAllWorkOrderItemByWorkOrderIdQuery query);
}
