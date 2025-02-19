package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticFindingResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticFindingResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiagnosticFindingIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void TestGetAllDiagnosticFinding_ValidDiagnosticId_ShouldPass() {
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

        CreateDiagnosticFindingResource createDiagnosticFindingResource1 = new CreateDiagnosticFindingResource(
                "Finding description",
                "LOW",
                100
        );

        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse1 = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticFindingResource1),
                DiagnosticFindingResource.class,
                diagnosticId
        );

        Assertions.assertThat(createdDiagnosticFindingResponse1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse1.getBody()).isNotNull();

        CreateDiagnosticFindingResource createDiagnosticFindingResource2 = new CreateDiagnosticFindingResource(
                "Finding description",
                "HIGH",
                200
        );

        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse2 = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticFindingResource2),
                DiagnosticFindingResource.class,
                diagnosticId
        );

        Assertions.assertThat(createdDiagnosticFindingResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse2.getBody()).isNotNull();

        // When
        ResponseEntity<DiagnosticFindingResource[]> diagnosticFindingsResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                DiagnosticFindingResource[].class,
                diagnosticId
        );

        // Then
        Assertions.assertThat(diagnosticFindingsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(diagnosticFindingsResponse.getBody()).isNotNull();
        Assertions.assertThat(diagnosticFindingsResponse.getBody()).hasSize(2);
    }

    @Test
    void TestCreateDiagnosticFinding_ValidResource_ShouldPass() {
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

        // When
        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticFindingResource),
                DiagnosticFindingResource.class,
                diagnosticId
        );

        // Then
        Assertions.assertThat(createdDiagnosticFindingResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody()).isNotNull();
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().description()).isEqualTo(createDiagnosticFindingResource.description());
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().severity()).isEqualTo(createDiagnosticFindingResource.severity());
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().estimatedRepairCost()).isEqualTo(createDiagnosticFindingResource.estimatedRepairCost());
    }
}