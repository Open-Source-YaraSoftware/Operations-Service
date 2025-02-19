package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateEvidenceResource;
import org.springframework.web.multipart.MultipartFile;

public class CreateDiagnosticEvidenceCommandFromResourceAssembler {
    public static CreateEvidenceCommand toCommandFromResource(
            String diagnosticId,
            String diagnosticFindingId,
            MultipartFile file,
            CreateEvidenceResource resource) {
        return new CreateEvidenceCommand(
            diagnosticId,
            diagnosticFindingId,
            file,
            resource.comment()
        );
    }
}
