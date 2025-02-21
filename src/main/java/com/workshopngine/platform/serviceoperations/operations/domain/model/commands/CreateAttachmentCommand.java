package com.workshopngine.platform.serviceoperations.operations.domain.model.commands;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateAttachmentCommand(
        String diagnosticId,
        String diagnosticFindingId,
        MultipartFile file,
        String description
) {
}
