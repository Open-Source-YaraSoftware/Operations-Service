package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private FileId fileId;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "diagnostic_finding_id", nullable = false)
    private DiagnosticFinding diagnosticFinding;

    public Evidence() {
    }

    public Evidence(CreateEvidenceCommand command, DiagnosticFinding diagnosticFinding, FileId fileId) {
        this.fileId = fileId;
        this.comment = command.comment();
        this.diagnosticFinding = diagnosticFinding;
    }
}
