package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateExecutedProcedureCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateServiceOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.ServiceOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

@ExtendWith(MockitoExtension.class)
class ServiceOrderCommandServiceImplTest {
    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private ServiceOrderCommandServiceImpl serviceOrderCommandServiceImpl;

    @Captor
    private ArgumentCaptor<ServiceOrder> serviceOrderArgumentCaptor;

    private static final String VALID_SERVICE_TYPE = "REPAIR";
    private static final String VALID_WORKSHOP_ID = "0f89d3f2-0d95-4b46-bd9f-c73cc7ef5955";
    private static final String VALID_VEHICLE_ID = "f6a2f271-fda3-4099-8ca5-5d5620841d53";
    private static final String VALID_CLIENT_ID = "f5741603-ee90-48b5-a699-32268e81685d";
    private static final String VALID_MECHANIC_ASSIGNED_ID = "61219089-c749-4631-bb2a-3ce6face2902";
    private static final String VALID_WORK_ORDER_ID = "a3ea0bc7-27e7-4484-a7bd-a12a81841bef";
    private static final String VALID_SERVICE_ORDER_STATUS = "PENDING";
    private static final String VALID_SERVICE_ORDER_ID = "61219089-c749-4631-bb2a-3ce6face2902";
    private static final String VALID_STANDARD_PROCEDURE_ID = "61219089-c749-4631-bb2a-3ce6face2902";

    @Test
    void TestCreateServiceOrder_ValidResource_ShouldPass() {
        // Given
        CreateServiceOrderCommand createServiceOrderCommand = buildCreateServiceOrderCommand();

        // When
        serviceOrderCommandServiceImpl.handle(createServiceOrderCommand);

        // Then
        Mockito.verify(serviceOrderRepository).save(serviceOrderArgumentCaptor.capture());
        ServiceOrder serviceOrder = serviceOrderArgumentCaptor.getValue();

        Assertions.assertThat(serviceOrder.getServiceType().toString())
                .as("Service Type")
                .isEqualTo(VALID_SERVICE_TYPE);

        Assertions.assertThat(serviceOrder.getWorkshopId())
                .as("Workshop ID")
                .isNotNull()
                .extracting("workshopId")
                .isEqualTo(VALID_WORKSHOP_ID);

        Assertions.assertThat(serviceOrder.getVehicleId())
                .as("Vehicle ID")
                .isNotNull()
                .extracting("vehicleId")
                .isEqualTo(VALID_VEHICLE_ID);

        Assertions.assertThat(serviceOrder.getClientId())
                .as("Client ID")
                .isNotNull()
                .extracting("clientId")
                .isEqualTo(VALID_CLIENT_ID);

        Assertions.assertThat(serviceOrder.getAssignedMechanicId())
                .as("Assigned Mechanic ID")
                .isNotNull()
                .extracting("mechanicId")
                .isEqualTo(VALID_MECHANIC_ASSIGNED_ID);

        Assertions.assertThat(serviceOrder.getWorkOrderId())
                .as("Work Order ID")
                .isNotNull()
                .extracting("workOrderId")
                .isEqualTo(VALID_WORK_ORDER_ID);

        Assertions.assertThat(serviceOrder.getStatus().toString())
                .as("Status")
                .isEqualTo(VALID_SERVICE_ORDER_STATUS);

        Assertions.assertThat(serviceOrder.getTotalTimeSpent())
                .as("Total Time Spent")
                .isEqualTo(Duration.ZERO);
    }

    @Test
    void TestCreateExecutedProcedure_ValidResource_ShouldPass() {
        // Given
        CreateServiceOrderCommand createServiceOrderCommand = buildCreateServiceOrderCommand();
        ServiceOrder serviceOrder = buildServiceOrder(createServiceOrderCommand);

        Mockito.when(serviceOrderRepository.findById(Mockito.anyString()))
                .thenReturn(java.util.Optional.of(serviceOrder));

        CreateExecutedProcedureCommand createExecutedProcedureCommand = buildCreateExecutedProcedureCommand();

        // When
        serviceOrderCommandServiceImpl.handle(createExecutedProcedureCommand);

        // Then
        Mockito.verify(serviceOrderRepository).save(serviceOrderArgumentCaptor.capture());
        ServiceOrder updatedServiceOrder = serviceOrderArgumentCaptor.getValue();

        Assertions.assertThat(updatedServiceOrder.getExecutedProcedures())
                .as("Executed Procedures")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("standardProcedureId", "name", "description", "estimatedTime", "actualTimeSpent", "outcome", "status")
                .containsExactly( new StandardProcedureId(VALID_STANDARD_PROCEDURE_ID), "Procedure Name", "Procedure Description", Duration.ofMinutes(60), Duration.ZERO, null, EExecutedProcedureStatus.PENDING);
    }

    private CreateServiceOrderCommand buildCreateServiceOrderCommand() {
        return CreateServiceOrderCommand.builder()
                .serviceType(EServiceType.fromString(VALID_SERVICE_TYPE))
                .workshopId(new WorkshopId(VALID_WORKSHOP_ID))
                .vehicleId(new VehicleId(VALID_VEHICLE_ID))
                .clientId(new ClientId(VALID_CLIENT_ID))
                .assignedMechanicId(new MechanicId(VALID_MECHANIC_ASSIGNED_ID))
                .workOrderId(new WorkOrderId(VALID_WORK_ORDER_ID))
                .build();
    }

    private ServiceOrder buildServiceOrder(CreateServiceOrderCommand command) {
        ServiceOrder serviceOrder = new ServiceOrder(command);
        serviceOrder.setId(VALID_SERVICE_ORDER_ID);
        return serviceOrder;
    }

    private CreateExecutedProcedureCommand buildCreateExecutedProcedureCommand() {
        return CreateExecutedProcedureCommand.builder()
                .serviceOrderId(VALID_SERVICE_ORDER_ID)
                .standardProcedureId(new StandardProcedureId(VALID_STANDARD_PROCEDURE_ID))
                .name("Procedure Name")
                .description("Procedure Description")
                .estimatedTime(Duration.ofMinutes(60))
                .build();
    }
}