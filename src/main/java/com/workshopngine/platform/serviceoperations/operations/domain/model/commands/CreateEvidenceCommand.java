package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

public record CreateEvidenceCommand(
        String diagnosticId,
        String diagnosticFindingId,
        MultipartFile file,
        String comment
) {
}
