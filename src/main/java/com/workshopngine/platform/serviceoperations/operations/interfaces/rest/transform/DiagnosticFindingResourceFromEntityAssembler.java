package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticFindingResource;

public class DiagnosticFindingResourceFromEntityAssembler {
    public static DiagnosticFindingResource toResourceFromEntity(DiagnosticFinding entity){
        return new DiagnosticFindingResource(
                entity.getId(),
                entity.getDiagnostic().getId(),
                entity.getDescription(),
                entity.getSeverity().toString(),
                entity.getEstimatedRepairCost()
        );
    }
}
