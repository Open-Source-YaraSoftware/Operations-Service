package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateAttachmentResource;
import org.springframework.web.multipart.MultipartFile;

public class CreateDiagnosticAttachmentCommandFromResourceAssembler {
    public static CreateAttachmentCommand toCommandFromResource(
            String diagnosticId,
            String diagnosticFindingId,
            MultipartFile file,
            CreateAttachmentResource resource) {
        return new CreateAttachmentCommand(
            diagnosticId,
            diagnosticFindingId,
            file,
            resource.comment()
        );
    }
}
