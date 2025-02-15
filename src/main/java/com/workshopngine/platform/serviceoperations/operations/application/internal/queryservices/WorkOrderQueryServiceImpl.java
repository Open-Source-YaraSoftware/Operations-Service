package com.workshopngine.platform.serviceoperations.operations.application.internal.queryservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetWorkOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;

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
}
