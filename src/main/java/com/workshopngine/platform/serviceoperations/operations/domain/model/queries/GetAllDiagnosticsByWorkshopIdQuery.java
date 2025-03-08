package com.workshopngine.platform.serviceoperations.operations.domain.model.queries;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;

public record GetAllDiagnosticsByWorkshopIdQuery(WorkshopId workshopId) {
    public GetAllDiagnosticsByWorkshopIdQuery {
        if (workshopId == null) {
            throw new IllegalArgumentException("Workshop ID must not be null");
        }
    }
}
