package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;

public record CreateWorkOrderCommand(
        ClientId clientId,
        VehicleId vehicleId,
        AppointmentId appointmentId,
        WorkshopId workshopId,
        MechanicId mechanicId,
        EPriority priority,
        ERequestType requestType,
        EServiceType serviceType
) {
}
