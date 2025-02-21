package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.application.internal.outboundservices.acl.FileManagementContextFacade;
import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateAttachmentCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.DiagnosticRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DiagnosticCommandServiceImplTest {
    @Mock
    private DiagnosticRepository diagnosticRepository;

    @Mock
    private FileManagementContextFacade fileManagementContextFacade;

    @InjectMocks
    private DiagnosticCommandServiceImpl diagnosticCommandService;

    @Captor
    private ArgumentCaptor<Diagnostic> diagnosticArgumentCaptor;

    private static final String DIAGNOSTIC_ID = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String INVALID_DIAGNOSTIC_ID = "111111c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String DIAGNOSTIC_FINDING_ID = "1a132d-5e6f-7g8h-9i0j-a1b123123123f6";
    private static final String WORKSHOP_ID_VALUE = "134534553d-5e6f-7g8h-9i0j-a1b12313535f6";
    private static final String VEHICLE_ID_VALUE = "6868678d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String MECHANIC_ID_VALUE = "3435567d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
    private static final String FILE_ID_VALUE = "0240525e6f-7g8h-9i0j-a1b2c3d4e5f6";

    @Test
    void TestCreateDiagnostic_ValidData_ShouldPass() {
        // Given
        CreateDiagnosticCommand command = buildCreateDiagnosticCommand();

        // When
        diagnosticCommandService.handle(command);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic diagnostic = diagnosticArgumentCaptor.getValue();

        Assertions.assertThat(diagnostic.getWorkshopId())
                .as("Workshop ID")
                .isNotNull()
                .extracting("workshopId")
                .isEqualTo(command.workshopId().workshopId());
        Assertions.assertThat(diagnostic.getVehicleId())
                .as("Vehicle ID")
                .isNotNull()
                .extracting("vehicleId")
                .isEqualTo(command.vehicleId().vehicleId());
        Assertions.assertThat(diagnostic.getMechanicId())
                .as("Mechanic ID")
                .isNotNull()
                .extracting("mechanicId")
                .isEqualTo(command.mechanicId().mechanicId());
        Assertions.assertThat(diagnostic.getDiagnosticType())
                .as("Diagnostic type")
                .isEqualTo(command.diagnosticType());
        Assertions.assertThat(diagnostic.getDesiredOutcome())
                .as("Desired outcome")
                .isEqualTo(command.desiredOutcome());
        Assertions.assertThat(diagnostic.getDetails())
                .as("Details")
                .isEqualTo(command.details());
    }

    @Test
    void TestCreateDiagnosticFinding_ValidDiagnosticId_ValidData_ShouldPass() {
        // Given
        CreateDiagnosticCommand createDiagnosticCommand = buildCreateDiagnosticCommand();
        Diagnostic existingDiagnostic = buildDiagnostic(createDiagnosticCommand);

        Mockito.when(diagnosticRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(existingDiagnostic));

        CreateDiagnosticFindingCommand findingCommand = buildCreateDiagnosticFindingCommand(DIAGNOSTIC_ID);

        // When
        diagnosticCommandService.handle(findingCommand);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic updatedDiagnostic = diagnosticArgumentCaptor.getValue();

        Assertions.assertThat(updatedDiagnostic.getFindings())
                .as("Diagnostic findings")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("description", "severity", "proposedSolution", "estimatedRepairCost", "remarks")
                .containsExactly("details", EFindingSeverity.CRITICAL, null, BigDecimal.valueOf(100), null);
    }

    @Test
    void TestCreateDiagnosticFinding_InvalidDiagnosticId_ValidData_ShouldThrowIllegalArgumentException() {
        // Given
        CreateDiagnosticFindingCommand findingCommand = buildCreateDiagnosticFindingCommand(INVALID_DIAGNOSTIC_ID);

        Mockito.when(diagnosticRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // When

        // Then
        Assertions.assertThatThrownBy(() -> diagnosticCommandService.handle(findingCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Diagnostic with ID %s not found".formatted(INVALID_DIAGNOSTIC_ID));
    }

    @Test
    void TestCreateDiagnosticAttachment_ValidData_ShouldPass() {
        // Given
        CreateDiagnosticCommand createDiagnosticCommand = CreateDiagnosticCommand.builder()
                .workshopId(new WorkshopId(WORKSHOP_ID_VALUE))
                .vehicleId(new VehicleId(VEHICLE_ID_VALUE))
                .mechanicId(new MechanicId(MECHANIC_ID_VALUE))
                .diagnosticType(EDiagnosticType.PREVENTIVE)
                .desiredOutcome("desired outcome")
                .details("details")
                .build();
        CreateDiagnosticFindingCommand createFindingCommand = CreateDiagnosticFindingCommand.builder()
                .diagnosticId(DIAGNOSTIC_ID)
                .description("details")
                .severity(EFindingSeverity.CRITICAL)
                .estimatedRepairCost(BigDecimal.valueOf(100))
                .build();

        Diagnostic createdDiagnostic = new Diagnostic(createDiagnosticCommand);
        createdDiagnostic.setId(DIAGNOSTIC_ID);

        DiagnosticFinding createdDiagnosticFinding = new DiagnosticFinding(createFindingCommand, createdDiagnostic);
        createdDiagnosticFinding.setId(DIAGNOSTIC_FINDING_ID);

        createdDiagnostic.setFindings(List.of(createdDiagnosticFinding));

        Mockito.when(diagnosticRepository.findById(DIAGNOSTIC_ID))
                .thenReturn(Optional.of(createdDiagnostic));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "Fake Image Content".getBytes()
        );

        CreateAttachmentCommand createAttachmentCommand = CreateAttachmentCommand.builder()
                .diagnosticId(DIAGNOSTIC_ID)
                .diagnosticFindingId(DIAGNOSTIC_FINDING_ID)
                .file(mockFile)
                .description("description test")
                .build();

        Mockito.when(fileManagementContextFacade.fetchUploadFile(Mockito.any(MultipartFile.class)))
                .thenReturn(new FileIdFeign(FILE_ID_VALUE));

        // When
        diagnosticCommandService.handle(createAttachmentCommand);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic updatedDiagnostic = diagnosticArgumentCaptor.getValue();
        Assertions.assertThat(updatedDiagnostic).isNotNull();
        Assertions.assertThat(updatedDiagnostic.getFindings())
                .as("Diagnostic findings")
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(updatedDiagnostic.getFindings().stream().findFirst().get().getAttachments())
                .as("Diagnostic finding evidences")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("description", "fileId")
                .containsExactly("description test", new FileId(FILE_ID_VALUE));
    }

    private CreateDiagnosticCommand buildCreateDiagnosticCommand() {
        return CreateDiagnosticCommand.builder()
                .workshopId(new WorkshopId(WORKSHOP_ID_VALUE))
                .vehicleId(new VehicleId(VEHICLE_ID_VALUE))
                .mechanicId(new MechanicId(MECHANIC_ID_VALUE))
                .diagnosticType(EDiagnosticType.PREVENTIVE)
                .desiredOutcome("desired outcome")
                .details("details")
                .build();
    }

    private Diagnostic buildDiagnostic(CreateDiagnosticCommand command) {
        Diagnostic diagnostic = new Diagnostic(command);
        diagnostic.setId(DIAGNOSTIC_ID);
        return diagnostic;
    }

    private CreateDiagnosticFindingCommand buildCreateDiagnosticFindingCommand(String diagnosticId) {
        return CreateDiagnosticFindingCommand.builder()
                .diagnosticId(diagnosticId)
                .description("details")
                .severity(EFindingSeverity.CRITICAL)
                .estimatedRepairCost(BigDecimal.valueOf(100))
                .build();
    }
}