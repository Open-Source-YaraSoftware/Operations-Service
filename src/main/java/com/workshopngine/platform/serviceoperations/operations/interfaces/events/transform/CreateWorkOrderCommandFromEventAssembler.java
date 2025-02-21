package com.workshopngine.platform.serviceoperations.operations.interfaces.events.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.events.AppointmentCreatedEvent;

public class CreateWorkOrderCommandFromEventAssembler {
    public static CreateWorkOrderCommand toCommandFromEvent(AppointmentCreatedEvent event) {
        return new CreateWorkOrderCommand(
                new ClientId(event.getClientId()),
                new VehicleId(event.getVehicleId()),
                new AppointmentId(event.getAppointmentId()),
                new WorkshopId(event.getWorkshopId()),
                new MechanicId(event.getMechanicId()),
                null,
                null,
                EServiceType.fromString(event.getServiceType())
        );
    }
}
