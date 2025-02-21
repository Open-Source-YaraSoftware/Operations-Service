package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateWorkOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderItemResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderResource;
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
class WorkOrderItemIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String WORK_ORDER_RESOURCE = "/work-orders";
    private static final String WORK_ORDER_ITEM_RESOURCE = "/work-orders/{workOrderId}/items";
    private static final String CLIENT_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VEHICLE_ID = "f6a2f271-fda3-4099-8ca5-5d5620841d53";
    private static final String APPOINTMENT_ID = "a3ea0bc7-27e7-4484-a7bd-a12a81841bef";
    private static final String WORKSHOP_ID = "0f89d3f2-0d95-4b46-bd9f-c73cc7ef5955";
    private static final String MECHANIC_ASSIGNED_ID = "61219089-c749-4631-bb2a-3ce6face2902";

    @Test
    void TestGetAllWorkOrderItem_ValidWorkOrderId_ShouldPass() {
        // Given
        CreateWorkOrderResource createWorkOrderResource = createWorkOrderResource();

        ResponseEntity<WorkOrderResource> createWorkOrderResponse = createWorkOrderResponse(createWorkOrderResource);

        Assertions.assertThat(createWorkOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createWorkOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createWorkOrderResponse.getBody().id()).isNotNull();
        String workOrderId = Objects.requireNonNull(createWorkOrderResponse.getBody()).id();

        // When
        ResponseEntity<WorkOrderItemResource[]> getALlWorkOrderItemResponse = getALlWorkOrderItemResponse(workOrderId);

        // Then
        Assertions.assertThat(getALlWorkOrderItemResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()).isNotNull();
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody().length).isEqualTo(1);
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()[0].id()).isNotNull();
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()[0].serviceType()).isEqualTo("REPAIR");
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()[0].serviceId()).isEmpty();
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()[0].itemStatus()).isEqualTo("PENDING");
        Assertions.assertThat(getALlWorkOrderItemResponse.getBody()[0].followUpFromItemId()).isEmpty();
    }

    private CreateWorkOrderResource createWorkOrderResource() {
        return CreateWorkOrderResource.builder()
                .clientId(CLIENT_ID)
                .vehicleId(VEHICLE_ID)
                .appointmentId(APPOINTMENT_ID)
                .workshopId(WORKSHOP_ID)
                .mechanicAssignedId(MECHANIC_ASSIGNED_ID)
                .priority("MEDIUM")
                .requestType("IMMEDIATE")
                .serviceType("REPAIR")
                .build();
    }

    private ResponseEntity<WorkOrderResource> createWorkOrderResponse(CreateWorkOrderResource createWorkOrderResource) {
        return testRestTemplate.exchange(
                WORK_ORDER_RESOURCE,
                HttpMethod.POST,
                new HttpEntity<>(createWorkOrderResource),
                WorkOrderResource.class
        );
    }

    private ResponseEntity<WorkOrderItemResource[]> getALlWorkOrderItemResponse(String workOrderId) {
        return testRestTemplate.exchange(
                WORK_ORDER_ITEM_RESOURCE,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                WorkOrderItemResource[].class,
                workOrderId
        );
    }
}