package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ProposedSolution;
import com.workshopngine.platform.serviceoperations.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Embedded
    private ProposedSolution proposedSolution;

    @NotNull
    private BigDecimal estimatedRepairCost;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EFindingStatus status;

    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "diagnosticFinding")
    private Collection<MediaAttachment> attachments;

    public DiagnosticFinding() {
        super();
        this.status = EFindingStatus.OPEN;
        this.attachments = new ArrayList<>();
    }

    public DiagnosticFinding(CreateDiagnosticFindingCommand command, Diagnostic diagnostic) {
        this();
        this.diagnostic = diagnostic;
        this.description = command.description();
        this.severity = command.severity();
        this.proposedSolution = command.proposedSolution();
        this.estimatedRepairCost = command.estimatedRepairCost();
        this.remarks = command.remarks();
    }

    public MediaAttachment addAttachment(CreateAttachmentCommand command, FileId fileId){
        var evidence = new MediaAttachment(command, this, fileId);
        this.attachments.add(evidence);
        return evidence;
    }

    public void markAsResolved() {
        this.status = EFindingStatus.RESOLVED;
    }
}
