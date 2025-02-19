package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

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

    @Test
    void TestGetDiagnostic_ValidId_ShouldPass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = new CreateDiagnosticResource(
            "1",
            "1",
            "9bc16276-ad51-4216-a0f7-2aef0668c5c5",
            "Reason for diagnostic",
            "Expected outcome",
            "Diagnostic procedure"
        );
        ResponseEntity<DiagnosticResource> createDiagnosticResponse = testRestTemplate.exchange(
            "/diagnostics",
            HttpMethod.POST,
            new HttpEntity<>(createDiagnosticResource),
            DiagnosticResource.class
        );
        Assertions.assertThat(createDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String diagnosticId = Objects.requireNonNull(createDiagnosticResponse.getBody()).id();

        // When
        ResponseEntity<DiagnosticResource> getDiagnosticResponse = testRestTemplate.exchange(
            "/diagnostics/" + diagnosticId,
            HttpMethod.GET,
            null,
            DiagnosticResource.class
        );

        // Then
        Assertions.assertThat(getDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getDiagnosticResponse.getBody()).isNotNull();
        Assertions.assertThat(getDiagnosticResponse.getBody().id()).isEqualTo(diagnosticId);
        Assertions.assertThat(getDiagnosticResponse.getBody().workshopId()).isEqualTo(createDiagnosticResource.workshopId());
        Assertions.assertThat(getDiagnosticResponse.getBody().vehicleId()).isEqualTo(createDiagnosticResource.vehicleId());
        Assertions.assertThat(getDiagnosticResponse.getBody().mechanicId()).isEqualTo(createDiagnosticResource.mechanicId());
        Assertions.assertThat(getDiagnosticResponse.getBody().reasonForDiagnostic()).isEqualTo(createDiagnosticResource.reasonForDiagnostic());
        Assertions.assertThat(getDiagnosticResponse.getBody().expectedOutcome()).isEqualTo(createDiagnosticResource.expectedOutcome());
        Assertions.assertThat(getDiagnosticResponse.getBody().diagnosticProcedure()).isEqualTo(createDiagnosticResource.diagnosticProcedure());
        Assertions.assertThat(getDiagnosticResponse.getBody().status()).isEqualTo("PENDING");
    }

    @Test
    void TestCreateDiagnostic_ValidResource_Should_Pass() {
        // Given
        CreateDiagnosticResource createDiagnosticResource = new CreateDiagnosticResource(
            "1",
            "1",
            "9bc16276-ad51-4216-a0f7-2aef0668c5c5",
            "Reason for diagnostic",
            "Expected outcome",
            "Diagnostic procedure"
        );
        // When
        ResponseEntity<DiagnosticResource> createDiagnosticResponse = testRestTemplate.exchange(
            "/diagnostics",
            HttpMethod.POST,
            new HttpEntity<>(createDiagnosticResource),
            DiagnosticResource.class
        );
        // Then
        Assertions.assertThat(createDiagnosticResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createDiagnosticResponse.getBody()).isNotNull();
        Assertions.assertThat(createDiagnosticResponse.getBody().id()).isNotNull();
        Assertions.assertThat(createDiagnosticResponse.getBody().workshopId()).isEqualTo(createDiagnosticResource.workshopId());
        Assertions.assertThat(createDiagnosticResponse.getBody().vehicleId()).isEqualTo(createDiagnosticResource.vehicleId());
        Assertions.assertThat(createDiagnosticResponse.getBody().mechanicId()).isEqualTo(createDiagnosticResource.mechanicId());
        Assertions.assertThat(createDiagnosticResponse.getBody().reasonForDiagnostic()).isEqualTo(createDiagnosticResource.reasonForDiagnostic());
        Assertions.assertThat(createDiagnosticResponse.getBody().expectedOutcome()).isEqualTo(createDiagnosticResource.expectedOutcome());
        Assertions.assertThat(createDiagnosticResponse.getBody().diagnosticProcedure()).isEqualTo(createDiagnosticResource.diagnosticProcedure());
        Assertions.assertThat(createDiagnosticResponse.getBody().status()).isEqualTo("PENDING");
    }
}