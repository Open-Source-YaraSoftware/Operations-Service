package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EDiagnosticType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticResource;
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

import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiagnosticIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String WORKSHOP_ID_VALUE = "134534553d-5e6f-7g8h-9i0j-a1b12313535f6";
    private static final String VEHICLE_ID_VALUE = "6868678d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String MECHANIC_ID_VALUE = "3435567d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";

    @Test
    void TestGetDiagnostic_ValidId_ShouldPass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = buildCreateDiagnosticResource();
        ResponseEntity<DiagnosticResource> createDiagnosticResponse = createDiagnosticResponse(createDiagnosticResource);
        Assertions.assertThat(createDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String diagnosticId = Objects.requireNonNull(createDiagnosticResponse.getBody()).id();

        // When
        ResponseEntity<DiagnosticResource> getDiagnosticResponse = getDiagnosticResponse(diagnosticId);

        // Then
        Assertions.assertThat(getDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getDiagnosticResponse.getBody()).isNotNull();
        Assertions.assertThat(getDiagnosticResponse.getBody().id()).isEqualTo(diagnosticId);
        Assertions.assertThat(getDiagnosticResponse.getBody().workshopId()).isEqualTo(createDiagnosticResource.workshopId());
        Assertions.assertThat(getDiagnosticResponse.getBody().vehicleId()).isEqualTo(createDiagnosticResource.vehicleId());
        Assertions.assertThat(getDiagnosticResponse.getBody().mechanicId()).isEqualTo(createDiagnosticResource.mechanicId());
        Assertions.assertThat(getDiagnosticResponse.getBody().diagnosticType()).isEqualTo("PREVENTIVE");
        Assertions.assertThat(getDiagnosticResponse.getBody().desiredOutcome()).isEqualTo(createDiagnosticResource.desiredOutcome());
        Assertions.assertThat(getDiagnosticResponse.getBody().details()).isEqualTo(createDiagnosticResource.details());
        Assertions.assertThat(getDiagnosticResponse.getBody().completedAt()).isNull();
        Assertions.assertThat(getDiagnosticResponse.getBody().status()).isEqualTo("PENDING");
    }

    @Test
    void TestCreateDiagnostic_ValidResource_Should_Pass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = buildCreateDiagnosticResource();
        // When
        ResponseEntity<DiagnosticResource> createDiagnosticResponse = createDiagnosticResponse(createDiagnosticResource);
        // Then
        Assertions.assertThat(createDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createDiagnosticResponse.getBody()).isNotNull();
        Assertions.assertThat(createDiagnosticResponse.getBody().id()).isNotNull();
        Assertions.assertThat(createDiagnosticResponse.getBody().workshopId()).isEqualTo(createDiagnosticResource.workshopId());
        Assertions.assertThat(createDiagnosticResponse.getBody().vehicleId()).isEqualTo(createDiagnosticResource.vehicleId());
        Assertions.assertThat(createDiagnosticResponse.getBody().mechanicId()).isEqualTo(createDiagnosticResource.mechanicId());
        Assertions.assertThat(createDiagnosticResponse.getBody().diagnosticType()).isEqualTo("PREVENTIVE");
        Assertions.assertThat(createDiagnosticResponse.getBody().desiredOutcome()).isEqualTo(createDiagnosticResource.desiredOutcome());
        Assertions.assertThat(createDiagnosticResponse.getBody().details()).isEqualTo(createDiagnosticResource.details());
        Assertions.assertThat(createDiagnosticResponse.getBody().completedAt()).isNull();
        Assertions.assertThat(createDiagnosticResponse.getBody().status()).isEqualTo("PENDING");
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
}