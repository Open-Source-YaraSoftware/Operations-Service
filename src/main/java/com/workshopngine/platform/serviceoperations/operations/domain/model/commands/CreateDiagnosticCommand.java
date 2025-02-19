package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.DiagnosticDetails;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;

public record CreateDiagnosticCommand(
        WorkshopId workshopId,
        VehicleId vehicleId,
        MechanicId mechanicId,
        DiagnosticDetails diagnosticDetails
) {
}
