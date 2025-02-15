package com.workshopngine.platform.serviceoperations.operations.interfaces.events.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.events.AppointmentCreatedEvent;

public class CreateWorkOrderCommandFromEventAssembler {
    public static CreateWorkOrderCommand toCommandFromEvent(AppointmentCreatedEvent event) {
        return new CreateWorkOrderCommand(
            new ClientId(event.clientId()),
                new VehicleId(event.vehicleId()),
                new AppointmentId(event.appointmentId()),
                new WorkshopId(event.workshopId()),
                new MechanicId(event.mechanicId()),
                EPriority.LOW,
                ERequestType.SCHEDULED
        );
    }
}
