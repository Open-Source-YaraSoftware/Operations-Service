package com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.MediaAttachment;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Diagnostic extends AuditableAbstractAggregateRoot<Diagnostic> {
    @Embedded
    private WorkshopId workshopId;

    @Embedded
    private VehicleId vehicleId;

    @Embedded
    private MechanicId mechanicId;

    @Enumerated(EnumType.STRING)
    private EDiagnosticType diagnosticType;

    private String desiredOutcome;

    @NotBlank
    private String details;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EDiagnosticStatus status;

    private LocalDateTime completedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "diagnostic")
    private Collection<DiagnosticFinding> findings;

    public Diagnostic() {
        super();
        this.details = Strings.EMPTY;
        this.desiredOutcome = Strings.EMPTY;
        this.status = EDiagnosticStatus.PENDING;
        this.completedAt = null;
        this.findings = new ArrayList<>();
    }

    public Diagnostic(CreateDiagnosticCommand command){
        this();
        this.workshopId = command.workshopId();
        this.vehicleId = command.vehicleId();
        this.mechanicId = command.mechanicId();
        this.diagnosticType = command.diagnosticType();
        this.desiredOutcome = command.desiredOutcome();
        this.details = command.details();
    }

    public DiagnosticFinding addFinding(CreateDiagnosticFindingCommand command){
        var diagnosticFinding = new DiagnosticFinding(command, this);
        this.findings.add(diagnosticFinding);
        return diagnosticFinding;
    }

    public DiagnosticFinding getFindingById(String diagnosticFindingId){
        return this.findings.stream()
                .filter(diagnosticFinding -> diagnosticFinding.getId().equals(diagnosticFindingId))
                .findFirst()
                .orElseThrow();
    }

    public MediaAttachment addAttachment(CreateAttachmentCommand command, FileId fileId){
        var diagnosticFinding = getFindingById(command.diagnosticFindingId());
        return diagnosticFinding.addAttachment(command, fileId);
    }

    public void complete(){
        this.status = EDiagnosticStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void cancel(){
        this.status = EDiagnosticStatus.CANCELLED;
        this.completedAt = LocalDateTime.now();
    }
}
