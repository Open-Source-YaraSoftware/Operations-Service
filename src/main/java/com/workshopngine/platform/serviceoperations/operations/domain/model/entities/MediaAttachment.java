package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@Entity
public class MediaAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private FileId fileId;

    private String description;

    @ManyToOne
    @JoinColumn(name = "diagnostic_finding_id", nullable = false)
    private DiagnosticFinding diagnosticFinding;

    public MediaAttachment() {
        this.description = Strings.EMPTY;
    }

    public MediaAttachment(CreateAttachmentCommand command, DiagnosticFinding diagnosticFinding, FileId fileId) {
        this.fileId = fileId;
        this.description = command.description();
        this.diagnosticFinding = diagnosticFinding;
    }
}
