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

import java.time.Duration;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExecutedStepIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String SERVICE_ORDER_RESOURCE = "/service-orders";
    private static final String EXECUTED_PROCEDURE_RESOURCE = "/service-orders/{serviceOrderId}/executed-procedures";
    private static final String EXECUTED_STEP_RESOURCE = "/service-orders/{serviceOrderId}/executed-procedures/{executedProcedureId}/executed-steps";
    private static final String VALID_STANDARD_PROCEDURE_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VALID_STANDARD_PROCEDURE_NAME = "Change oil";
    private static final String VALID_STANDARD_PROCEDURE_DESCRIPTION = "Change the oil of the vehicle";
    private static final String VALID_STANDARD_PROCEDURE_ESTIMATED_TIME = "PT1H30M";
    private static final String VALID_SERVICE_TYPE = "REPAIR";
    private static final String VALID_WORKSHOP_ID = "0f89d3f2-0d95-4b46-bd9f-c73cc7ef5955";
    private static final String VALID_VEHICLE_ID = "f6a2f271-fda3-4099-8ca5-5d5620841d53";
    private static final String VALID_CLIENT_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VALID_MECHANIC_ASSIGNED_ID = "61219089-c749-4631-bb2a-3ce6face2902";
    private static final String VALID_WORK_ORDER_ID = "a3ea0bc7-27e7-4484-a7bd-a12a81841bef";
    private static final String VALID_STANDARD_STEP_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VALID_ASSIGNED_MECHANIC_ID = "61219089-c749-4631-bb2a-3ce6face2902";
    private static final Integer VALID_ORDER = 1;
    private static final String VALID_NAME = "Change oil";
    private static final String VALID_DESCRIPTION = "Change the oil of the vehicle";
    private static final Duration VALID_ESTIMATED_TIME = Duration.parse("PT1H30M");
    private static final Boolean VALID_QUALITY_CHECK_REQUIRED = false;

    @Test
    void TestCreateExecutedStep_ValidResource_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createServiceOrderResponse.getBody().id()).isNotBlank();
        String serviceOrderId = createServiceOrderResponse.getBody().id();

        CreateExecutedProcedureResource createExecutedProcedureResource = buildCreateExecutedProcedureResource();
        ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse = createExecutedProcedureResponse(createExecutedProcedureResource, serviceOrderId);

        Assertions.assertThat(createExecutedProcedureResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedProcedureResponse.getBody()).isNotNull();

        ResponseEntity<ExecutedProcedureResource[]> getAllExecutedProceduresByServiceOrderIdResponse = getAllExecutedProceduresByServiceOrderIdResponse(serviceOrderId);

        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getBody()).isNotNull();
        String executedProcedureId = getAllExecutedProceduresByServiceOrderIdResponse.getBody()[0].id();

        CreateExecutedStepResource createExecutedStepResource = buildCreateExecutedStepResource();

        // When
        ResponseEntity<ExecutedStepResource> createExecutedStepResponse = createExecutedStepResponse(createExecutedStepResource, serviceOrderId, executedProcedureId);

        // Then
        Assertions.assertThat(createExecutedStepResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedStepResponse.getBody()).isNotNull();
    }

    private CreateServiceOrderResource buildCreateServiceOrderResource() {
        return CreateServiceOrderResource.builder()
                .serviceType(VALID_SERVICE_TYPE)
                .workshopId(VALID_WORKSHOP_ID)
                .vehicleId(VALID_VEHICLE_ID)
                .clientId(VALID_CLIENT_ID)
                .assignedMechanicId(VALID_MECHANIC_ASSIGNED_ID)
                .workOrderId(VALID_WORK_ORDER_ID)
                .build();
    }

    private ResponseEntity<ServiceOrderResource> createServiceOrderResponse(CreateServiceOrderResource createServiceOrderResource) {
        return testRestTemplate.exchange(
                SERVICE_ORDER_RESOURCE,
                HttpMethod.POST,
                new HttpEntity<>(createServiceOrderResource),
                ServiceOrderResource.class
        );
    }

    private CreateExecutedProcedureResource buildCreateExecutedProcedureResource() {
        return CreateExecutedProcedureResource.builder()
                .standardProcedureId(VALID_STANDARD_PROCEDURE_ID)
                .name(VALID_STANDARD_PROCEDURE_NAME)
                .description(VALID_STANDARD_PROCEDURE_DESCRIPTION)
                .estimatedTime(Duration.parse(VALID_STANDARD_PROCEDURE_ESTIMATED_TIME))
                .build();
    }

    private ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse(CreateExecutedProcedureResource createExecutedProcedureResource, String serviceOrderId) {
        return testRestTemplate.exchange(
                EXECUTED_PROCEDURE_RESOURCE,
                HttpMethod.POST,
                new HttpEntity<>(createExecutedProcedureResource),
                ExecutedProcedureResource.class,
                serviceOrderId
        );
    }

    private ResponseEntity<ExecutedProcedureResource[]> getAllExecutedProceduresByServiceOrderIdResponse(String serviceOrderId) {
        return testRestTemplate.exchange(
                EXECUTED_PROCEDURE_RESOURCE,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ExecutedProcedureResource[].class,
                serviceOrderId
        );
    }

    private CreateExecutedStepResource buildCreateExecutedStepResource() {
        return CreateExecutedStepResource.builder()
                .standardStepId(VALID_STANDARD_STEP_ID)
                .assignedMechanicId(VALID_ASSIGNED_MECHANIC_ID)
                .stepOrder(VALID_ORDER)
                .name(VALID_NAME)
                .description(VALID_DESCRIPTION)
                .estimatedTime(VALID_ESTIMATED_TIME)
                .qualityCheckRequired(VALID_QUALITY_CHECK_REQUIRED)
                .build();
    }

    private ResponseEntity<ExecutedStepResource> createExecutedStepResponse(CreateExecutedStepResource createExecutedStepResource, String serviceOrderId, String executedProcedureId) {
        return testRestTemplate.exchange(
                EXECUTED_STEP_RESOURCE,
                HttpMethod.POST,
                new HttpEntity<>(createExecutedStepResource),
                ExecutedStepResource.class,
                serviceOrderId,
                executedProcedureId
        );
    }
}