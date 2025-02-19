package com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Evidence;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @Embedded
    private DiagnosticDetails diagnosticDetails;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EDiagnosticStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "diagnostic")
    private Collection<DiagnosticFinding> diagnosticFindings;

    public Diagnostic() {
        super();
        this.status = EDiagnosticStatus.PENDING;
        this.diagnosticFindings = new ArrayList<>();
    }

    public Diagnostic(CreateDiagnosticCommand command){
        this();
        this.workshopId = command.workshopId();
        this.vehicleId = command.vehicleId();
        this.mechanicId = command.mechanicId();
        this.diagnosticDetails = command.diagnosticDetails();
    }

    public DiagnosticFinding addDiagnosticFinding(CreateDiagnosticFindingCommand command){
        var diagnosticFinding = new DiagnosticFinding(command, this);
        this.diagnosticFindings.add(diagnosticFinding);
        return diagnosticFinding;
    }

    public DiagnosticFinding getDiagnosticFindingById(String diagnosticFindingId){
        return this.diagnosticFindings.stream()
                .filter(diagnosticFinding -> diagnosticFinding.getId().equals(diagnosticFindingId))
                .findFirst()
                .orElseThrow();
    }

    public Recommendation addRecommendation(CreateRecommendationCommand command){
        var diagnosticFinding = getDiagnosticFindingById(command.diagnosticFindingId());
        return diagnosticFinding.addRecommendation(command);
    }

    public Evidence addEvidence(CreateEvidenceCommand command, FileId fileId){
        var diagnosticFinding = getDiagnosticFindingById(command.diagnosticFindingId());
        return diagnosticFinding.addEvidence(command, fileId);
    }
}
