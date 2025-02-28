package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedStepCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EExecutedStepStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EQualityCheckStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.StandardStepId;
import com.workshopngine.platform.serviceoperations.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ExecutedStep extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "executed_procedure_id", nullable = false)
    private ExecutedProcedure executedProcedure;

    @Embedded
    private StandardStepId standardStepId;

    @NotNull
    private MechanicId assignedMechanicId;

    @NotNull
    private Integer stepOrder;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Duration estimatedTime;

    private Duration actualTimeSpent;

    @NotNull
    private Boolean qualityCheckRequired;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EQualityCheckStatus qualityCheckStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EExecutedStepStatus status;

    @ManyToOne
    @JoinColumn(name = "executed_step_id", nullable = true)
    private ExecutedStep parentStep;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentStep")
    private Collection<ExecutedStep> subSteps;

    public ExecutedStep() {
        super();
        this.standardStepId = new StandardStepId();
        this.stepOrder = 0;
        this.name = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.estimatedTime = Duration.ZERO;
        this.actualTimeSpent = Duration.ZERO;
        this.qualityCheckRequired = false;
        this.status = EExecutedStepStatus.PENDING;
        this.qualityCheckStatus = EQualityCheckStatus.NOT_REQUIRED;
        this.subSteps = new ArrayList<>();
    }

    public ExecutedStep(CreateExecutedStepCommand command, ExecutedProcedure executedProcedure) {
        this();
        this.executedProcedure = executedProcedure;
        this.standardStepId = command.standardStepId();
        this.assignedMechanicId = command.assignedMechanicId();
        this.stepOrder = command.stepOrder();
        this.name = command.name();
        this.description = command.description();
        this.estimatedTime = command.estimatedTime();
        this.qualityCheckRequired = command.qualityCheckRequired();
        this.qualityCheckStatus = this.qualityCheckRequired ? EQualityCheckStatus.PENDING : EQualityCheckStatus.NOT_REQUIRED;
    }
}
