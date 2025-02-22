package com.workshopngine.platform.serviceoperations.operations.application.internal.queryservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllExecutedProceduresByServiceOrderIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetExecutedProcedureByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetServiceOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ServiceOrderQueryServiceImpl implements ServiceOrderQueryService {
    private final ServiceOrderRepository serviceOrderRepository;

    @Override
    public Optional<ServiceOrder> handle(GetServiceOrderByIdQuery query) {
        return serviceOrderRepository.findById(query.serviceOrderId());
    }

    @Override
    public Optional<ExecutedProcedure> handle(GetExecutedProcedureByIdQuery query) {
        var serviceOrder = serviceOrderRepository.findById(query.serviceOrderId());
        if (serviceOrder.isEmpty()) throw new IllegalArgumentException("Service with id %s not found".formatted(query.serviceOrderId()));
        var executedProcedure = serviceOrder.get().findExecutedProcedureById(query.executedProcedureId());
        return Optional.ofNullable(executedProcedure);
    }

    @Override
    public Collection<ExecutedProcedure> handle(GetAllExecutedProceduresByServiceOrderIdQuery query) {
        var serviceOrder = serviceOrderRepository.findById(query.serviceOrderId());
        if (serviceOrder.isEmpty()) throw new IllegalArgumentException("Service with id %s not found".formatted(query.serviceOrderId()));
        return serviceOrder.get().getExecutedProcedures();
    }
}
