package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Evidence;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.EvidenceResource;

public class EvidenceResourceFromEntityAssembler {
    public static EvidenceResource toResourceFromEntity(Evidence evidence) {
        return new EvidenceResource(
                evidence.getId(),
                evidence.getFileId().fileId(),
                evidence.getComment()
        );
    }
}
