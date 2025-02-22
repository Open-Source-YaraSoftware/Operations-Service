package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import lombok.Builder;

@Builder
public record CreateServiceOrderCommand(
    EServiceType serviceType,
    WorkshopId workshopId,
    VehicleId vehicleId,
    ClientId clientId,
    MechanicId assignedMechanicId,
    WorkOrderId workOrderId
) {
}
