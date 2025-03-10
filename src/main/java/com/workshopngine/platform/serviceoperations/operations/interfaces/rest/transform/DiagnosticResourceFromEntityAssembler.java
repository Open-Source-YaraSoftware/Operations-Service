package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticResource;

public class DiagnosticResourceFromEntityAssembler {
    public static DiagnosticResource toResourceFromEntity(Diagnostic entity) {
        return new DiagnosticResource(
                entity.getId(),
                entity.getWorkshopId().workshopId(),
                entity.getVehicleId().vehicleId(),
                entity.getMechanicId().mechanicId(),
                entity.getDiagnosticType().toString(),
                entity.getDesiredOutcome(),
                entity.getDetails(),
                entity.getStatus().toString(),
                entity.getCompletedAt()
        );
    }
}
