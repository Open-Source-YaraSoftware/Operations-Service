package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.MediaAttachment;

import java.util.Optional;

public interface DiagnosticCommandService {
    String handle(CreateDiagnosticCommand command);
    Optional<DiagnosticFinding> handle(CreateDiagnosticFindingCommand command);
    Optional<MediaAttachment> handle(CreateAttachmentCommand command);
}
