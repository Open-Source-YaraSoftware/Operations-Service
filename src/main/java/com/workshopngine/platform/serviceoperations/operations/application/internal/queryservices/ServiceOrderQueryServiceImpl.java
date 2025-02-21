package com.workshopngine.platform.serviceoperations.operations.application.internal.queryservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetServiceOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceOrderQueryServiceImpl implements ServiceOrderQueryService {
    @Override
    public Optional<ServiceOrder> handle(GetServiceOrderByIdQuery query) {
        return Optional.empty();
    }
}
