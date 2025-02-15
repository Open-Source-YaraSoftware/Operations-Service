package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateWorkOrderResource;

public class CreateWorkOrderCommandFromResourceAssembler {
    public static CreateWorkOrderCommand toCommandFromResource(CreateWorkOrderResource resource) {
        return new CreateWorkOrderCommand(
                new ClientId(resource.clientId()),
                new VehicleId(resource.vehicleId()),
                new AppointmentId(resource.appointmentId()),
                new WorkshopId(resource.workshopId()),
                new MechanicId(resource.mechanicAssignedId()),
                resource.priority() != null ?  EPriority.fromString(resource.priority()): null,
                resource.requestType() != null ? ERequestType.fromString(resource.requestType()): null

        );
    }
}
