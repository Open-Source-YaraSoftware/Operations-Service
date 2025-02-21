package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.MediaAttachment;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.AttachmentResource;

public class AttachmentResourceFromEntityAssembler {
    public static AttachmentResource toResourceFromEntity(MediaAttachment mediaAttachment) {
        return new AttachmentResource(
                mediaAttachment.getId(),
                mediaAttachment.getFileId().fileId(),
                mediaAttachment.getDescription()
        );
    }
}
