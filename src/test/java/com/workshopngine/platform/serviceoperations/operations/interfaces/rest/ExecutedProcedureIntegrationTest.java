package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedProcedureResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateServiceOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedProcedureResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ServiceOrderResource;
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
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExecutedProcedureIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String SERVICE_ORDER_RESOURCE = "/service-orders";
    private static final String EXECUTED_PROCEDURE_RESOURCE = "/service-orders/{serviceOrderId}/executed-procedures";
    private static final String EXECUTED_PROCEDURE_RESOURCE_ID = "/service-orders/{serviceOrderId}/executed-procedures/{executedProcedureId}";
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
    private static final String VALID_SERVICE_ORDER_STATUS = "PENDING";

    @Test
    void TestCreateExecutedProcedure_ValidResource_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createServiceOrderResponse.getBody().id()).isNotBlank();
        String serviceOrderId = Objects.requireNonNull(createServiceOrderResponse.getBody()).id();

        CreateExecutedProcedureResource createExecutedProcedureResource = buildCreateExecutedProcedureResource();

        // When
        ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse = createExecutedProcedureResponse(createExecutedProcedureResource, serviceOrderId);

        // Then
        Assertions.assertThat(createExecutedProcedureResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedProcedureResponse.getBody()).isNotNull();
        Assertions.assertThat(createExecutedProcedureResponse.getBody().standardProcedureId()).isEqualTo(VALID_STANDARD_PROCEDURE_ID);
        Assertions.assertThat(createExecutedProcedureResponse.getBody().name()).isEqualTo(VALID_STANDARD_PROCEDURE_NAME);
        Assertions.assertThat(createExecutedProcedureResponse.getBody().description()).isEqualTo(VALID_STANDARD_PROCEDURE_DESCRIPTION);
        Assertions.assertThat(createExecutedProcedureResponse.getBody().estimatedTime()).isEqualTo(Duration.parse(VALID_STANDARD_PROCEDURE_ESTIMATED_TIME));
        Assertions.assertThat(createExecutedProcedureResponse.getBody().actualTimeSpent()).isEqualTo(Duration.ZERO);
        Assertions.assertThat(createExecutedProcedureResponse.getBody().outcome()).isNull();
        Assertions.assertThat(createExecutedProcedureResponse.getBody().status()).isEqualTo(VALID_SERVICE_ORDER_STATUS);
    }

    @Test
    void TestGetExecutedProcedure_ValidId_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createServiceOrderResponse.getBody().id()).isNotBlank();
        String serviceOrderId = Objects.requireNonNull(createServiceOrderResponse.getBody()).id();

        CreateExecutedProcedureResource createExecutedProcedureResource = buildCreateExecutedProcedureResource();
        ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse = createExecutedProcedureResponse(createExecutedProcedureResource, serviceOrderId);

        Assertions.assertThat(createExecutedProcedureResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedProcedureResponse.getBody()).isNotNull();

        ResponseEntity<ExecutedProcedureResource[]> getAllExecutedProceduresByServiceOrderIdResponse = getAllExecutedProceduresByServiceOrderIdResponse(serviceOrderId);

        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getBody()).isNotNull();
        String executedProcedureId = Objects.requireNonNull(getAllExecutedProceduresByServiceOrderIdResponse.getBody())[0].id();

        // When
        ResponseEntity<ExecutedProcedureResource> getExecutedProcedureResponse = getExecutedProcedureByIdResponse(serviceOrderId, executedProcedureId);

        // Then
        Assertions.assertThat(getExecutedProcedureResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getExecutedProcedureResponse.getBody()).isNotNull();
        Assertions.assertThat(getExecutedProcedureResponse.getBody().id()).isNotBlank();
        Assertions.assertThat(getExecutedProcedureResponse.getBody().standardProcedureId()).isEqualTo(VALID_STANDARD_PROCEDURE_ID);
        Assertions.assertThat(getExecutedProcedureResponse.getBody().name()).isEqualTo(VALID_STANDARD_PROCEDURE_NAME);
        Assertions.assertThat(getExecutedProcedureResponse.getBody().description()).isEqualTo(VALID_STANDARD_PROCEDURE_DESCRIPTION);
        Assertions.assertThat(getExecutedProcedureResponse.getBody().estimatedTime()).isEqualTo(Duration.parse(VALID_STANDARD_PROCEDURE_ESTIMATED_TIME));
        Assertions.assertThat(getExecutedProcedureResponse.getBody().actualTimeSpent()).isEqualTo(Duration.ZERO);
        Assertions.assertThat(getExecutedProcedureResponse.getBody().outcome()).isNull();
        Assertions.assertThat(getExecutedProcedureResponse.getBody().status()).isEqualTo(VALID_SERVICE_ORDER_STATUS);

    }

    @Test
    void TestGetAllExecutedProceduresByServiceOrderId_ValidServiceOrderId_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createServiceOrderResponse.getBody().id()).isNotBlank();
        String serviceOrderId = Objects.requireNonNull(createServiceOrderResponse.getBody()).id();

        CreateExecutedProcedureResource createExecutedProcedureResource1 = buildCreateExecutedProcedureResource();
        ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse1 = createExecutedProcedureResponse(createExecutedProcedureResource1, serviceOrderId);

        Assertions.assertThat(createExecutedProcedureResponse1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedProcedureResponse1.getBody()).isNotNull();

        CreateExecutedProcedureResource createExecutedProcedureResource2 = buildCreateExecutedProcedureResource();
        ResponseEntity<ExecutedProcedureResource> createExecutedProcedureResponse2 = createExecutedProcedureResponse(createExecutedProcedureResource2, serviceOrderId);

        Assertions.assertThat(createExecutedProcedureResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createExecutedProcedureResponse2.getBody()).isNotNull();

        // When
        ResponseEntity<ExecutedProcedureResource[]> getAllExecutedProceduresByServiceOrderIdResponse = getAllExecutedProceduresByServiceOrderIdResponse(serviceOrderId);

        // Then
        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getBody()).isNotNull();
        Assertions.assertThat(getAllExecutedProceduresByServiceOrderIdResponse.getBody()).hasSize(2);
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

    private ResponseEntity<ExecutedProcedureResource> getExecutedProcedureByIdResponse(String serviceOrderId, String executedProcedureId) {
        return testRestTemplate.exchange(
                EXECUTED_PROCEDURE_RESOURCE_ID,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ExecutedProcedureResource.class,
                serviceOrderId,
                executedProcedureId
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
}