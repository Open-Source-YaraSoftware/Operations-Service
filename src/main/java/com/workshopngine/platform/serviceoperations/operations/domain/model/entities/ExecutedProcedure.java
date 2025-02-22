package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EExecutedProcedureOutcome;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EExecutedProcedureStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardProcedureId;
import com.workshopngine.platform.serviceoperations.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.time.Duration;

@Entity
@Setter
@Getter
public class ExecutedProcedure extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "service_order_id", nullable = false)
    private ServiceOrder serviceOrder;

    @NotNull
    @Embedded
    private StandardProcedureId standardProcedureId;

    @NotBlank
    private String name;

    private String description;

    private Duration estimatedTime;

    private Duration actualTimeSpent;

    @Enumerated(EnumType.STRING)
    private EExecutedProcedureOutcome outcome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EExecutedProcedureStatus status;

    public ExecutedProcedure() {
        super();
        this.name = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.estimatedTime = Duration.ZERO;
        this.actualTimeSpent = Duration.ZERO;
        this.outcome = null;
        this.status = EExecutedProcedureStatus.PENDING;
    }

    public ExecutedProcedure(CreateExecutedProcedureCommand command, ServiceOrder serviceOrder) {
        this();
        this.serviceOrder = serviceOrder;
        this.standardProcedureId = command.standardProcedureId();
        this.name = command.name();
        this.description = command.description();
        this.estimatedTime = command.estimatedTime();
    }
}
