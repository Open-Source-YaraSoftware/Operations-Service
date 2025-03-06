package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderResource;

public class WorkOrderResourceFromEntityAssembler {
    public static WorkOrderResource toResourceFromEntity(WorkOrder entity) {
        return new WorkOrderResource(
                entity.getId(),
                entity.getClientId().clientId(),
                entity.getVehicleId().vehicleId(),
                entity.getAppointmentId().appointmentId(),
                entity.getWorkshopId().workshopId(),
                entity.getMechanicAssignedId().mechanicId(),
                entity.getStatus().toString(),
                entity.getPriority().toString(),
                entity.getRequestType().toString(),
                entity.getTotalEstimatedCost().getAmount(),
                entity.getTotalEstimatedCost().getCurrency(),
                entity.getTotalEstimatedCost().getCostStatus().toString()
        );
    }
}
