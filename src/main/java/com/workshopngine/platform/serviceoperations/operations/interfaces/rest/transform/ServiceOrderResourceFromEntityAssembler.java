package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ServiceOrderResource;

public class ServiceOrderResourceFromEntityAssembler {
    public static ServiceOrderResource toResourceFromEntity(ServiceOrder serviceOrder) {
        return ServiceOrderResource.builder()
                .id(serviceOrder.getId())
                .serviceType(serviceOrder.getServiceType().toString())
                .workshopId(serviceOrder.getWorkshopId().workshopId())
                .vehicleId(serviceOrder.getVehicleId().vehicleId())
                .clientId(serviceOrder.getClientId().clientId())
                .assignedMechanicId(serviceOrder.getAssignedMechanicId().mechanicId())
                .workOrderId(serviceOrder.getWorkOrderId().workOrderId())
                .status(serviceOrder.getStatus().toString())
                .totalTimeSpent(serviceOrder.getTotalTimeSpent())
                .build();
    }
}
