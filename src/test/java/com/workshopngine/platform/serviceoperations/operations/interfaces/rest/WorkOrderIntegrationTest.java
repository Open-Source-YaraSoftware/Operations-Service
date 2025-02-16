package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateWorkOrderResource;
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

import java.math.BigDecimal;
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkOrderIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getWorkOrderById() {
        // Given
        CreateWorkOrderResource createWorkOrderResource = new CreateWorkOrderResource(
                "22222",
                "3333",
                "6666",
                "11111",
                "585858",
                "LOW",
                "IMMEDIATE"
        );
        ResponseEntity<WorkOrderResource> createWorkOrderResponse = testRestTemplate.exchange(
                "/work-orders",
                HttpMethod.POST,
                new HttpEntity<>(createWorkOrderResource),
                WorkOrderResource.class
        );
        Assertions.assertThat(createWorkOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String workOrderId = Objects.requireNonNull(createWorkOrderResponse.getBody()).id();

        // When
        ResponseEntity<WorkOrderResource> getWorkOrderResponse = testRestTemplate.exchange(
                "/work-orders/" + workOrderId,
                HttpMethod.GET,
                null,
                WorkOrderResource.class
        );
        // Then
        Assertions.assertThat(getWorkOrderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getWorkOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(getWorkOrderResponse.getBody().id()).isEqualTo(workOrderId);
        Assertions.assertThat(getWorkOrderResponse.getBody().clientId()).isEqualTo(createWorkOrderResource.clientId());
        Assertions.assertThat(getWorkOrderResponse.getBody().vehicleId()).isEqualTo(createWorkOrderResource.vehicleId());
        Assertions.assertThat(getWorkOrderResponse.getBody().appointmentId()).isEqualTo(createWorkOrderResource.appointmentId());
        Assertions.assertThat(getWorkOrderResponse.getBody().workshopId()).isEqualTo(createWorkOrderResource.workshopId());
        Assertions.assertThat(getWorkOrderResponse.getBody().mechanicAssignedId()).isEqualTo(createWorkOrderResource.mechanicAssignedId());
        Assertions.assertThat(getWorkOrderResponse.getBody().status()).isEqualTo("CREATED");
        Assertions.assertThat(getWorkOrderResponse.getBody().priority()).isEqualTo(createWorkOrderResource.priority());
        Assertions.assertThat(getWorkOrderResponse.getBody().requestType()).isEqualTo(createWorkOrderResource.requestType());
        Assertions.assertThat(getWorkOrderResponse.getBody().totalCost().compareTo(BigDecimal.ZERO)).isEqualTo(0);
        Assertions.assertThat(getWorkOrderResponse.getBody().currency()).isEqualTo("PEN");
        Assertions.assertThat(getWorkOrderResponse.getBody().start()).isNotNull();
        Assertions.assertThat(getWorkOrderResponse.getBody().end()).isNull();
        Assertions.assertThat(getWorkOrderResponse.getBody().duration()).isNull();
    }

    @Test
    void createWorkOrder() {
        // Given
        CreateWorkOrderResource createWorkOrderResource = new CreateWorkOrderResource(
                "1111",
                "2222",
                "3333",
                "4444",
                "5555",
                "HIGH",
                "SCHEDULED"
        );
        // When
        ResponseEntity<WorkOrderResource> createWorkOrderResponse = testRestTemplate.exchange(
                "/work-orders",
                HttpMethod.POST,
                new HttpEntity<>(createWorkOrderResource),
                WorkOrderResource.class
        );
        // Then
        Assertions.assertThat(createWorkOrderResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createWorkOrderResponse.getBody()).isNotNull();
        Assertions.assertThat(createWorkOrderResponse.getBody().id()).isNotBlank();
        Assertions.assertThat(createWorkOrderResponse.getBody().clientId()).isEqualTo(createWorkOrderResource.clientId());
        Assertions.assertThat(createWorkOrderResponse.getBody().vehicleId()).isEqualTo(createWorkOrderResource.vehicleId());
        Assertions.assertThat(createWorkOrderResponse.getBody().appointmentId()).isEqualTo(createWorkOrderResource.appointmentId());
        Assertions.assertThat(createWorkOrderResponse.getBody().workshopId()).isEqualTo(createWorkOrderResource.workshopId());
        Assertions.assertThat(createWorkOrderResponse.getBody().mechanicAssignedId()).isEqualTo(createWorkOrderResource.mechanicAssignedId());
        Assertions.assertThat(createWorkOrderResponse.getBody().status()).isEqualTo("CREATED");
        Assertions.assertThat(createWorkOrderResponse.getBody().priority()).isEqualTo(createWorkOrderResource.priority());
        Assertions.assertThat(createWorkOrderResponse.getBody().requestType()).isEqualTo(createWorkOrderResource.requestType());
        Assertions.assertThat(createWorkOrderResponse.getBody().totalCost()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(createWorkOrderResponse.getBody().currency()).isEqualTo("PEN");
        Assertions.assertThat(createWorkOrderResponse.getBody().start()).isNotNull();
        Assertions.assertThat(createWorkOrderResponse.getBody().end()).isNull();
        Assertions.assertThat(createWorkOrderResponse.getBody().duration()).isNull();
    }
}