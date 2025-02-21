package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import lombok.Builder;

@Builder
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
