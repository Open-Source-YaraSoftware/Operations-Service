package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EDiagnosticType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticResource;

public class CreateDiagnosticCommandFromResourceAssembler {
    public static CreateDiagnosticCommand toCommandFromResource(CreateDiagnosticResource resource) {
        return new CreateDiagnosticCommand(
                new WorkshopId(resource.workshopId()),
                new VehicleId(resource.vehicleId()),
                new MechanicId(resource.mechanicId()),
                EDiagnosticType.valueOf(resource.diagnosticType()),
                resource.desiredOutcome(),
                resource.details()
        );
    }
}
