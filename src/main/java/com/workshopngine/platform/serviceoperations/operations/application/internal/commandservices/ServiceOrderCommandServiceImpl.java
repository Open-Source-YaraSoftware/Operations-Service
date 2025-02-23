package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedStep;
import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedStepCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<ExecutedProcedure> handle(CreateExecutedProcedureCommand command) {
        var serviceOrder = serviceOrderRepository.findById(command.serviceOrderId());
        if (serviceOrder.isEmpty()) throw new IllegalArgumentException("Service with id %s not found".formatted(command.serviceOrderId()));
        var executedProcedure = serviceOrder.get().addExecutedProcedure(command);
        try {
            serviceOrderRepository.save(serviceOrder.get());
            return Optional.of(executedProcedure);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving executed procedure: " + e.getMessage());
        }
    }

    @Override
    public Optional<ExecutedStep> handle(CreateExecutedStepCommand command) {
        return Optional.empty();
    }
}
