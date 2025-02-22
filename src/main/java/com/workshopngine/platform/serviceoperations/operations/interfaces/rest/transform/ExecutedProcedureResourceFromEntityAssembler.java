package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedProcedureResource;

public class ExecutedProcedureResourceFromEntityAssembler {
    public static ExecutedProcedureResource toResourceFromEntity(ExecutedProcedure executedProcedure) {
        return ExecutedProcedureResource.builder()
                .build();
    }
}
