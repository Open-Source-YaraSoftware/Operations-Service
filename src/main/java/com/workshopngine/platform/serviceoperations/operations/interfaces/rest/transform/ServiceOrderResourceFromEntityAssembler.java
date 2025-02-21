package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ServiceOrderResource;

public class ServiceOrderResourceFromEntityAssembler {
    public static ServiceOrderResource toResourceFromEntity(ServiceOrder serviceOrder) {
        return ServiceOrderResource.builder()
                .build();
    }
}
