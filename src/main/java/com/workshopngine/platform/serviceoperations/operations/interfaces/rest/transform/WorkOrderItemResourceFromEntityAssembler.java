package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.WorkOrderItem;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderItemResource;

public class WorkOrderItemResourceFromEntityAssembler {
    public static WorkOrderItemResource toResourceFromEntity(WorkOrderItem entity) {
        return WorkOrderItemResource.builder()
                .id(entity.getId())
                .serviceId(entity.getServiceId().serviceId())
                .serviceType(entity.getServiceType().toString())
                .itemStatus(entity.getItemStatus().toString())
                .followUpFromItemId(entity.getFollowUpFromItemId())
                .build();
    }
}
