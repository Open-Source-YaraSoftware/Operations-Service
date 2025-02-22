package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceOrderCommandServiceImpl implements ServiceOrderCommandService {
    private final ServiceOrderRepository serviceOrderRepository;

    @Override
    public String handle(CreateServiceOrderCommand command) {
        ServiceOrder workOrder = new ServiceOrder(command);
        try {
            serviceOrderRepository.save(workOrder);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving work order: " + e.getMessage());
        }
        return workOrder.getId();
    }
}
