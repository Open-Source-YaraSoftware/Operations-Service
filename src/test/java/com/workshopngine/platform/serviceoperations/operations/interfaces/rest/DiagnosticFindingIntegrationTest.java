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

import java.math.BigDecimal;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiagnosticFindingIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String WORKSHOP_ID_VALUE = "134534553d-5e6f-7g8h-9i0j-a1b12313535f6";
    private static final String VEHICLE_ID_VALUE = "6868678d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String MECHANIC_ID_VALUE = "3435567d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";

    @Test
    void TestGetAllDiagnosticFinding_ValidDiagnosticId_ShouldPass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = buildCreateDiagnosticResource();
        ResponseEntity<DiagnosticResource> createdDiagnosticResponse = createDiagnosticResponse(createDiagnosticResource);

        Assertions.assertThat(createdDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticResponse.getBody()).isNotNull();
        String diagnosticId = createdDiagnosticResponse.getBody().id();

        CreateDiagnosticFindingResource createDiagnosticFindingResource1 = buildCreateDiagnosticFindingResource();

        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse1 = createDiagnosticFindingResponse(createDiagnosticFindingResource1, diagnosticId);

        Assertions.assertThat(createdDiagnosticFindingResponse1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse1.getBody()).isNotNull();

        CreateDiagnosticFindingResource createDiagnosticFindingResource2 = buildCreateDiagnosticFindingResource();

        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse2 = createDiagnosticFindingResponse(createDiagnosticFindingResource2, diagnosticId);

        Assertions.assertThat(createdDiagnosticFindingResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse2.getBody()).isNotNull();

        // When
        ResponseEntity<DiagnosticFindingResource[]> diagnosticFindingsResponse = getAllDiagnosticFindingResponseByDiagnosticId(diagnosticId);

        // Then
        Assertions.assertThat(diagnosticFindingsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(diagnosticFindingsResponse.getBody()).isNotNull();
        Assertions.assertThat(diagnosticFindingsResponse.getBody()).hasSize(2);
    }

    @Test
    void TestCreateDiagnosticFinding_ValidResource_ShouldPass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = buildCreateDiagnosticResource();
        ResponseEntity<DiagnosticResource> createdDiagnosticResponse = createDiagnosticResponse(createDiagnosticResource);

        Assertions.assertThat(createdDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticResponse.getBody()).isNotNull();
        String diagnosticId = createdDiagnosticResponse.getBody().id();

        CreateDiagnosticFindingResource createDiagnosticFindingResource = buildCreateDiagnosticFindingResource();

        // When
        ResponseEntity<DiagnosticFindingResource> createdDiagnosticFindingResponse = createDiagnosticFindingResponse(createDiagnosticFindingResource, diagnosticId);

        // Then
        Assertions.assertThat(createdDiagnosticFindingResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody()).isNotNull();
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().description()).isEqualTo(createDiagnosticFindingResource.description());
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().severity()).isEqualTo(createDiagnosticFindingResource.severity());
        Assertions.assertThat(createdDiagnosticFindingResponse.getBody().estimatedRepairCost()).isEqualTo(createDiagnosticFindingResource.estimatedRepairCost());
    }

    private CreateDiagnosticResource buildCreateDiagnosticResource() {
        return CreateDiagnosticResource.builder()
                .workshopId(WORKSHOP_ID_VALUE)
                .vehicleId(VEHICLE_ID_VALUE)
                .mechanicId(MECHANIC_ID_VALUE)
                .diagnosticType("PREVENTIVE")
                .desiredOutcome("desired outcome")
                .details("details")
                .build();
    }

    private ResponseEntity<DiagnosticResource> createDiagnosticResponse(CreateDiagnosticResource createDiagnosticResource) {
        return testRestTemplate.exchange(
                "/diagnostics",
                HttpMethod.POST,
                new HttpEntity<>(createDiagnosticResource),
                DiagnosticResource.class
        );
    }

    private ResponseEntity<DiagnosticResource> getDiagnosticResponse(String diagnosticId) {
        return testRestTemplate.exchange(
                "/diagnostics/" + diagnosticId,
                HttpMethod.GET,
                null,
                DiagnosticResource.class
        );
    }

    private CreateDiagnosticFindingResource buildCreateDiagnosticFindingResource() {
        return CreateDiagnosticFindingResource.builder()
            .description("Finding description")
            .severity("LOW")
            .solutionDescription("solution description")
            .solutionType("IMMEDIATE")
            .estimatedRepairCost(BigDecimal.valueOf(100))
            .remarks("remarks")
            .build();
    }

    private ResponseEntity<DiagnosticFindingResource> createDiagnosticFindingResponse(CreateDiagnosticFindingResource createDiagnosticFindingResource, String diagnosticId) {
        return testRestTemplate.exchange(
            "/diagnostics/{diagnosticId}/findings",
            HttpMethod.POST,
            new HttpEntity<>(createDiagnosticFindingResource),
            DiagnosticFindingResource.class,
            diagnosticId
        );
    }

    private ResponseEntity<DiagnosticFindingResource[]> getAllDiagnosticFindingResponseByDiagnosticId(String diagnosticId) {
        return testRestTemplate.exchange(
            "/diagnostics/{diagnosticId}/findings",
            HttpMethod.GET,
            HttpEntity.EMPTY,
            DiagnosticFindingResource[].class,
            diagnosticId
        );
    }
}