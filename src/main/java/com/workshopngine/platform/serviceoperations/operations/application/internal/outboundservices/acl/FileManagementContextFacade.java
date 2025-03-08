package com.workshopngine.platform.serviceoperations.operations.application.internal.outboundservices.acl;

import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileIdFeign;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.feign.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "file-management",
        configuration = FeignSupportConfig.class
)
public interface FileManagementContextFacade {
    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileIdFeign fetchUploadFile(@RequestPart("file") MultipartFile file);
}
