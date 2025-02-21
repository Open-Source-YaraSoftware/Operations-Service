package com.workshopngine.platform.serviceoperations.shared.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AppointmentCreatedEvent extends ApplicationEvent implements DomainEvent {
    private final String appointmentId;
    private final String clientId;
    private final String vehicleId;
    private final String mechanicId;
    private final String workshopId;
    private final String serviceType;
    private final String appointmentDate;

    public AppointmentCreatedEvent(Object source, String appointmentId, String clientId, String vehicleId, String mechanicId, String workshopId, String serviceType, String appointmentDate) {
        super(source);
        this.appointmentId = appointmentId;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.mechanicId = mechanicId;
        this.workshopId = workshopId;
        this.serviceType = serviceType;
        this.appointmentDate = appointmentDate;
    }
}
