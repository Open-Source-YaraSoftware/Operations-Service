package com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.WorkOrderItem;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@Entity
public class WorkOrder extends AuditableAbstractAggregateRoot<WorkOrder> {
    @Embedded
    private ClientId clientId;

    @Embedded
    private VehicleId vehicleId;

    @Embedded
    private AppointmentId appointmentId;

    @Embedded
    private WorkshopId workshopId;

    @Embedded
    private MechanicId mechanicAssignedId;

    @Enumerated(EnumType.STRING)
    private EWorkOrderStatus status;

    @Enumerated(EnumType.STRING)
    private EPriority priority;

    @Enumerated(EnumType.STRING)
    private ERequestType requestType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "workOrder")
    private Collection<WorkOrderItem> items;

    @Embedded
    private CostEstimate totalEstimatedCost;

    public WorkOrder() {
        super();
        this.status = EWorkOrderStatus.CREATED;
        this.priority = EPriority.LOW;
        this.requestType = ERequestType.IMMEDIATE;
        this.totalEstimatedCost = new CostEstimate();
        this.items = new ArrayList<>();
    }

    public WorkOrder(CreateWorkOrderCommand command) {
        this();
        this.clientId = command.clientId();
        this.vehicleId = command.vehicleId();
        this.appointmentId = command.appointmentId();
        this.workshopId = command.workshopId();
        this.mechanicAssignedId = command.mechanicId();
        if (command.priority() != null) this.priority = command.priority();
        if (command.requestType() != null) this.requestType = command.requestType();
        this.addItem(command);
    }

    private void addItem(CreateWorkOrderCommand command) {
        WorkOrderItem item = new WorkOrderItem(this, command);
        this.items.add(item);
    }
}
