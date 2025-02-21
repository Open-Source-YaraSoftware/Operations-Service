package com.workshopngine.platform.serviceoperations.operations.application.internal.queryservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.WorkOrderItem;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllWorkOrderItemByWorkOrderIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetWorkOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderQueryServiceImpl implements WorkOrderQueryService {
    private final WorkOrderRepository workOrderRepository;

    public WorkOrderQueryServiceImpl(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;

    }

    @Override
    public Optional<WorkOrder> handle(GetWorkOrderByIdQuery query) {
        return workOrderRepository.findById(query.workOrderId());
    }

    @Override
    public Collection<WorkOrderItem> handle(GetAllWorkOrderItemByWorkOrderIdQuery query) {
        var workOrder = workOrderRepository.findById(query.workOrderId());
        if (workOrder.isEmpty()) throw new IllegalArgumentException("WorkOrder with %s id not found".formatted(query.workOrderId()));
        return workOrder.get().getItems();
    }
}
