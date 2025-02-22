package com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.ExecutedProcedure;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ServiceOrder extends AuditableAbstractAggregateRoot<ServiceOrder> {
    @NotNull
    private EServiceType serviceType;

    @NotNull
    @Embedded
    private WorkshopId workshopId;

    @NotNull
    @Embedded
    private VehicleId vehicleId;

    @NotNull
    @Embedded
    private ClientId clientId;

    @NotNull
    @Embedded
    private MechanicId assignedMechanicId;

    @NotNull
    @Embedded
    private WorkOrderId workOrderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EServiceOrderStatus status;

    private Duration totalTimeSpent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "serviceOrder")
    private Collection<ExecutedProcedure> executedProcedures;

    public ServiceOrder() {
        super();
        this.status = EServiceOrderStatus.PENDING;
        this.totalTimeSpent = Duration.ZERO;
        this.executedProcedures = new ArrayList<>();
    }

    public ServiceOrder(CreateServiceOrderCommand command) {
        this();
        this.serviceType = command.serviceType();
        this.workshopId = command.workshopId();
        this.vehicleId = command.vehicleId();
        this.clientId = command.clientId();
        this.assignedMechanicId = command.assignedMechanicId();
        this.workOrderId = command.workOrderId();
    }

    public ExecutedProcedure addExecutedProcedure(CreateExecutedProcedureCommand command) {
        ExecutedProcedure newExecutedProcedure = new ExecutedProcedure(command, this);
        this.executedProcedures.add(newExecutedProcedure);
        return newExecutedProcedure;
    }

    public ExecutedProcedure findExecutedProcedureById(String executedProcedureId) {
        return this.executedProcedures
                .stream()
                .filter(ep -> ep.getId().equals(executedProcedureId))
                .findFirst()
                .orElse(null);
    }
}
