package com.workshopngine.platform.serviceoperations.operations.interfaces.events;

import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.events.transform.CreateWorkOrderCommandFromEventAssembler;
import com.workshopngine.platform.serviceoperations.shared.domain.model.events.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentEventHandler {
    private final WorkOrderCommandService workOrderCommandService;

    @EventListener
    public void handleAppointmentCreatedEvent(AppointmentCreatedEvent event) {
        var createWorkOrderCommand = CreateWorkOrderCommandFromEventAssembler.toCommandFromEvent(event);
        workOrderCommandService.handle(createWorkOrderCommand);
    }
}
