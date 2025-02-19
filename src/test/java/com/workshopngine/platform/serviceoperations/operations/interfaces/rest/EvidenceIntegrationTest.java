package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EvidenceIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Disabled
    @Test
    void TestCreateEvidence_ValidResource_ShouldPass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = new CreateDiagnosticResource(
                "1",
                "1",
                "9bc16276-ad51-4216-a0f7-2aef0668c5c5",
                "Reason for diagnostic",
                "Expected outcome",
                "Diagnostic procedure"
        );
        ResponseEntity<DiagnosticResource> createdDiagnosticResponse = testRestTemplate.exchange(
                "/diagnostics",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticResource),
                DiagnosticResource.class
        );
        Assertions.assertThat(createdDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticResponse.getBody()).isNotNull();
        String diagnosticId = createdDiagnosticResponse.getBody().id();

        CreateDiagnosticFindingResource createDiagnosticFindingResource = new CreateDiagnosticFindingResource(
                "Finding description",
                "LOW",
                100
        );

        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticFindingResource),
                DiagnosticFindingResource.class,
                diagnosticId
        );

        Assertions.assertThat(createdDiagnosticFindingResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody()).isNotNull();

        ResponseEntity<DiagnosticFindingResource[]> diagnosticFindingsResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                DiagnosticFindingResource[].class,
                diagnosticId
        );
        Assertions.assertThat(diagnosticFindingsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(diagnosticFindingsResponse.getBody()).isNotNull();
        String findingId = diagnosticFindingsResponse.getBody()[0].id();

        CreateEvidenceResource createEvidenceResource = new CreateEvidenceResource(
                "Evidence comment"
        );

        ByteArrayResource fileResource = new ByteArrayResource("evidence".getBytes()) {
            @Override
            public String getFilename() {
                return "evidence.jpg";
            }
        };

        // When
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("resource", new HttpEntity<>(createEvidenceResource, createJsonHeaders()));
        body.add("file", new HttpEntity<>(fileResource, createFileHeaders()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<EvidenceResource> createdEvidenceResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings/{findingId}/evidences",
                HttpMethod.POST,
                requestEntity,
                EvidenceResource.class,
                diagnosticId,
                findingId
        );

        // Then
        Assertions.assertThat(createdEvidenceResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdEvidenceResponse.getBody()).isNotNull();
        Assertions.assertThat(createdEvidenceResponse.getBody().comment()).isEqualTo(createEvidenceResource.comment());
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders createFileHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return headers;
    }
}