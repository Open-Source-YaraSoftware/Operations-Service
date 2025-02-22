package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateServiceOrderResource;

public class CreateServiceOrderCommandFromResourceAssembler {
    public static CreateServiceOrderCommand toCommandFromResource(CreateServiceOrderResource resource) {
        return CreateServiceOrderCommand.builder()
                .serviceType(EServiceType.fromString(resource.serviceType()))
                .workshopId(new WorkshopId(resource.workshopId()))
                .vehicleId(new VehicleId(resource.vehicleId()))
                .clientId(new ClientId(resource.clientId()))
                .assignedMechanicId(new MechanicId(resource.assignedMechanicId()))
                .workOrderId(new WorkOrderId(resource.workOrderId()))
                .build();
    }
}
