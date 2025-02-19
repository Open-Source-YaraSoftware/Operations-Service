package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import com.workshopngine.platform.serviceoperations.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class DiagnosticFinding extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "diagnostic_id", nullable = false)
    private Diagnostic diagnostic;

    @NotBlank
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EFindingSeverity severity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "diagnosticFinding")
    private Collection<Evidence> evidences;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "diagnosticFinding")
    private Collection<Recommendation> recommendedActions;

    private Integer estimatedRepairCost;

    public DiagnosticFinding() {
        super();
        this.evidences = new ArrayList<>();
        this.recommendedActions = new ArrayList<>();
    }

    public DiagnosticFinding(CreateDiagnosticFindingCommand command, Diagnostic diagnostic) {
        this();
        this.diagnostic = diagnostic;
        this.description = command.description();
        this.severity = command.severity();
        this.estimatedRepairCost = command.estimatedRepairCost();
    }

    public Recommendation addRecommendation(CreateRecommendationCommand command){
        var recommendation = new Recommendation(command, this);
        this.recommendedActions.add(recommendation);
        return recommendation;
    }

    public Evidence addEvidence(CreateEvidenceCommand command, FileId fileId){
        var evidence = new Evidence(command, this, fileId);
        this.evidences.add(evidence);
        return evidence;
    }
}
