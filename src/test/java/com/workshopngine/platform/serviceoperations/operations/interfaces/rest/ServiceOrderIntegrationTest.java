package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateServiceOrderResource;
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
class ServiceOrderIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String SERVICE_ORDER_RESOURCE = "/service-orders";
    private static final String SERVICE_ORDER_RESOURCE_ID = "/service-orders/{id}";
    private static final String VALID_SERVICE_TYPE = "REPAIR";
    private static final String VALID_WORKSHOP_ID = "0f89d3f2-0d95-4b46-bd9f-c73cc7ef5955";
    private static final String VALID_VEHICLE_ID = "f6a2f271-fda3-4099-8ca5-5d5620841d53";
    private static final String VALID_CLIENT_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VALID_MECHANIC_ASSIGNED_ID = "61219089-c749-4631-bb2a-3ce6face2902";
    private static final String VALID_WORK_ORDER_ID = "a3ea0bc7-27e7-4484-a7bd-a12a81841bef";
    private static final String VALID_SERVICE_ORDER_STATUS = "PENDING";

    @Test
    void TestGetServiceOrder_ValidId_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String serviceOrderId = Objects.requireNonNull(createServiceOrderResponse.getBody()).id();

        // When
        ResponseEntity<ServiceOrderResource> getServiceOrderResponse = getServiceOrderResponse(serviceOrderId);

        // Then
        Assertions.assertThat(getServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(getServiceOrderResponse.getBody().id()).isNotBlank();
        Assertions.assertThat(getServiceOrderResponse.getBody().serviceType()).isEqualTo(createServiceOrderResource.serviceType());
        Assertions.assertThat(getServiceOrderResponse.getBody().workshopId()).isEqualTo(createServiceOrderResource.workshopId());
        Assertions.assertThat(getServiceOrderResponse.getBody().vehicleId()).isEqualTo(createServiceOrderResource.vehicleId());
        Assertions.assertThat(getServiceOrderResponse.getBody().clientId()).isEqualTo(createServiceOrderResource.clientId());
        Assertions.assertThat(getServiceOrderResponse.getBody().assignedMechanicId()).isEqualTo(createServiceOrderResource.assignedMechanicId());
        Assertions.assertThat(getServiceOrderResponse.getBody().workOrderId()).isEqualTo(createServiceOrderResource.workOrderId());
        Assertions.assertThat(getServiceOrderResponse.getBody().status()).isEqualTo(VALID_SERVICE_ORDER_STATUS);
        Assertions.assertThat(getServiceOrderResponse.getBody().totalTimeSpent()).isEqualTo(Duration.ZERO);
    }

    @Test
    void TestCreateServiceOrder_ValidResource_ShouldPass() {
        // Given
        CreateServiceOrderResource createServiceOrderResource = buildCreateServiceOrderResource();

        // When
        ResponseEntity<ServiceOrderResource> createServiceOrderResponse = createServiceOrderResponse(createServiceOrderResource);

        // Then
        Assertions.assertThat(createServiceOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createServiceOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createServiceOrderResponse.getBody().id()).isNotBlank();
        Assertions.assertThat(createServiceOrderResponse.getBody().serviceType()).isEqualTo(createServiceOrderResource.serviceType());
        Assertions.assertThat(createServiceOrderResponse.getBody().workshopId()).isEqualTo(createServiceOrderResource.workshopId());
        Assertions.assertThat(createServiceOrderResponse.getBody().vehicleId()).isEqualTo(createServiceOrderResource.vehicleId());
        Assertions.assertThat(createServiceOrderResponse.getBody().clientId()).isEqualTo(createServiceOrderResource.clientId());
        Assertions.assertThat(createServiceOrderResponse.getBody().assignedMechanicId()).isEqualTo(createServiceOrderResource.assignedMechanicId());
        Assertions.assertThat(createServiceOrderResponse.getBody().workOrderId()).isEqualTo(createServiceOrderResource.workOrderId());
        Assertions.assertThat(createServiceOrderResponse.getBody().status()).isEqualTo(VALID_SERVICE_ORDER_STATUS);
        Assertions.assertThat(createServiceOrderResponse.getBody().totalTimeSpent()).isEqualTo(Duration.ZERO);
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

    private ResponseEntity<ServiceOrderResource> getServiceOrderResponse(String serviceOrderId) {
        return testRestTemplate.exchange(
                SERVICE_ORDER_RESOURCE_ID,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ServiceOrderResource.class,
                serviceOrderId
        );
    }
}