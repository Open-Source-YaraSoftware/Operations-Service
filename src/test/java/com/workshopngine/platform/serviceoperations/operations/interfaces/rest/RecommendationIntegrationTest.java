package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.*;
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
class RecommendationIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void TestGetAllRecommendations_ValidDiagnosticId_ShouldPass() {
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

        CreateRecommendationResource createRecommendationResource1 = new CreateRecommendationResource(
                "Recommendation content"
        );

        ResponseEntity<RecommendationResource> createdRecommendationResponse1 = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings/{findingId}/recommendation",
                HttpMethod.POST,
                new HttpEntity<>(createRecommendationResource1),
                RecommendationResource.class,
                diagnosticId,
                findingId
        );

        Assertions.assertThat(createdRecommendationResponse1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdRecommendationResponse1.getBody()).isNotNull();

        CreateRecommendationResource createRecommendationResource2 = new CreateRecommendationResource(
                "Recommendation content"
        );

        ResponseEntity<RecommendationResource> createdRecommendationResponse2 = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings/{findingId}/recommendation",
                HttpMethod.POST,
                new HttpEntity<>(createRecommendationResource2),
                RecommendationResource.class,
                diagnosticId,
                findingId
        );

        Assertions.assertThat(createdRecommendationResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdRecommendationResponse2.getBody()).isNotNull();

        // When
        ResponseEntity<RecommendationResource[]> recommendationsResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings/{findingId}/recommendation",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                RecommendationResource[].class,
                diagnosticId,
                findingId
        );

        // Then
        Assertions.assertThat(recommendationsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(recommendationsResponse.getBody()).isNotNull();
        Assertions.assertThat(recommendationsResponse.getBody()).hasSize(2);
    }

    @Test
    void TestCreateRecommendation_ValidResource_ShouldPass() {
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

        CreateRecommendationResource createRecommendationResource = new CreateRecommendationResource(
                "Recommendation content"
        );

        // When
        ResponseEntity<RecommendationResource> createdRecommendationResponse = testRestTemplate.exchange(
                "/diagnostics/{diagnosticId}/findings/{findingId}/recommendation",
                HttpMethod.POST,
                new HttpEntity<>(createRecommendationResource),
                RecommendationResource.class,
                diagnosticId,
                findingId
        );

        // Then
        Assertions.assertThat(createdRecommendationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdRecommendationResponse.getBody()).isNotNull();
        Assertions.assertThat(createdRecommendationResponse.getBody().content()).isEqualTo(createRecommendationResource.content());
    }
}