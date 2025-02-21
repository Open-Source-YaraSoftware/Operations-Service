package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record WorkOrderItemResource(
        String id,
        String serviceId,
        String serviceType,
        String itemStatus,
        String followUpFromItemId
) {
}
