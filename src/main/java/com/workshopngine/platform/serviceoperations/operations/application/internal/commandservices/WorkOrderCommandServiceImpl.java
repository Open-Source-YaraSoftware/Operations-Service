package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderCommandServiceImpl implements WorkOrderCommandService {
    private final WorkOrderRepository workOrderRepository;

    public WorkOrderCommandServiceImpl(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @Override
    public String handle(CreateWorkOrderCommand command) {
        var workOrder = new WorkOrder(command);
        try {
            workOrderRepository.save(workOrder);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving work order: " + e.getMessage());
        }
        return workOrder.getId();
    }
}
