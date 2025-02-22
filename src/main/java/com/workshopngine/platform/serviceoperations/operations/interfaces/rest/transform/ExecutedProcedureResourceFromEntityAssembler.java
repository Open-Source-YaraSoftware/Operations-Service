package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedProcedureResource;

public class ExecutedProcedureResourceFromEntityAssembler {
    public static ExecutedProcedureResource toResourceFromEntity(ExecutedProcedure executedProcedure) {
        return ExecutedProcedureResource.builder()
                .id(executedProcedure.getId())
                .standardProcedureId(executedProcedure.getStandardProcedureId().standardProcedureId())
                .name(executedProcedure.getName())
                .description(executedProcedure.getDescription())
                .estimatedTime(executedProcedure.getEstimatedTime())
                .actualTimeSpent(executedProcedure.getActualTimeSpent())
                .outcome(executedProcedure.getOutcome() != null ? executedProcedure.getOutcome().toString() : null)
                .status(executedProcedure.getStatus().toString())
                .build();
    }
}
