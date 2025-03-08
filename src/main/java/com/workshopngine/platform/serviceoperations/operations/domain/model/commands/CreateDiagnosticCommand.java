package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EDiagnosticType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;
import lombok.Builder;

@Builder
public record CreateDiagnosticCommand(
        WorkshopId workshopId,
        VehicleId vehicleId,
        MechanicId mechanicId,
        EDiagnosticType diagnosticType,
        String desiredOutcome,
        String details
) {
}
