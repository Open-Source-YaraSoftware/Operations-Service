package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;

public record GetAllDiagnosticsByVehicleIdQuery(VehicleId vehicleId) {
    public GetAllDiagnosticsByVehicleIdQuery {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID must not be null");
        }
    }
}
