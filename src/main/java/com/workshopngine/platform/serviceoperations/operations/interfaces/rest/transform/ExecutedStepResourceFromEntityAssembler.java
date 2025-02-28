package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedStep;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedStepResource;

public class ExecutedStepResourceFromEntityAssembler {
    public static ExecutedStepResource toResourceFromEntity(ExecutedStep entity) {
        return ExecutedStepResource.builder()
                .id(entity.getId())
                .standardStepId(entity.getStandardStepId().standardStepId())
                .assignedMechanicId(entity.getAssignedMechanicId().mechanicId())
                .stepOrder(entity.getStepOrder())
                .name(entity.getName())
                .description(entity.getDescription())
                .estimatedTime(entity.getEstimatedTime())
                .actualTimeSpent(entity.getActualTimeSpent())
                .qualityCheckRequired(entity.getQualityCheckRequired())
                .qualityCheckStatus(entity.getQualityCheckStatus().toString())
                .status(entity.getStatus().toString())
                .build(
        );
    }
}
