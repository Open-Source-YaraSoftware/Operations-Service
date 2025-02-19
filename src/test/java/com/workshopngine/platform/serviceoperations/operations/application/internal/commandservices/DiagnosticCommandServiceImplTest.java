package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.application.internal.outboundservices.acl.FileManagementContextFacade;
import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.*;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.DiagnosticRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiagnosticCommandServiceImplTest {
    @Mock
    private DiagnosticRepository diagnosticRepository;

    @Mock
    private FileManagementContextFacade fileManagementContextFacade;

    @Captor
    private ArgumentCaptor<Diagnostic> diagnosticArgumentCaptor;

    private DiagnosticCommandService diagnosticCommandService;

    @BeforeEach
    void setUp() {
        diagnosticCommandService = new DiagnosticCommandServiceImpl(diagnosticRepository, fileManagementContextFacade);
    }

    @Test
    void TestCreateDiagnostic_ValidData_ShouldPass() {
        // Given
        CreateDiagnosticCommand command = new CreateDiagnosticCommand(
                new WorkshopId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new VehicleId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new MechanicId("1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new DiagnosticDetails("details", "details", "details")
        );
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
        Assertions.assertThat(diagnostic.getDiagnosticDetails())
                .as("Diagnostic details")
                .isNotNull()
                .extracting("reasonForDiagnostic", "expectedOutcome", "diagnosticProcedure")
                .containsExactly("details", "details", "details");
    }

    @Test
    void TestCreateDiagnosticFinding_ValidDiagnosticId_ValidData_ShouldPass() {
        // Given
        CreateDiagnosticCommand createDiagnosticCommand = new CreateDiagnosticCommand(
                new WorkshopId("1a2b312d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new VehicleId("123b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new MechanicId("1a22344d-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new DiagnosticDetails("details", "details", "details")
        );
        var createdDiagnostic = new Diagnostic(createDiagnosticCommand);

        Mockito.when(diagnosticRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(createdDiagnostic));

        CreateDiagnosticFindingCommand commandFinding = new CreateDiagnosticFindingCommand(
                "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6",
                "details",
                EFindingSeverity.CRITICAL,
                100
        );

        // When
        diagnosticCommandService.handle(commandFinding);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic updatedDiagnostic = diagnosticArgumentCaptor.getValue();
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings())
                .as("Diagnostic findings")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("description", "severity", "estimatedRepairCost")
                .containsExactly("details", EFindingSeverity.CRITICAL, 100);
    }

    @Test
    void TestCreateDiagnosticFinding_InvalidDiagnosticId_ValidData_ShouldThrowIllegalArgumentException() {
        // Given
        String invalidDiagnosticId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";

        CreateDiagnosticFindingCommand command = new CreateDiagnosticFindingCommand(
                invalidDiagnosticId,
                "details",
                EFindingSeverity.CRITICAL,
                100
        );

        Mockito.when(diagnosticRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // When

        // Then
        Assertions.assertThatThrownBy(() -> diagnosticCommandService.handle(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Diagnostic with ID %s not found".formatted(invalidDiagnosticId));
    }

    @Test
    void TestCreateDiagnosticEvidence_ValidData_ShouldPass() {
        // Given
        var validDiagnosticId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
        var validDiagnosticFindingId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
        var validFileId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";

        var createDiagnosticCommand = new CreateDiagnosticCommand(
                new WorkshopId("1a2b3123123123-7g8h-9i0j-a1b2c3d4e5f6"),
                new VehicleId("123b345-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new MechanicId("1a2456456-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new DiagnosticDetails("details", "details", "details")
        );
        var createFindingCommand = new CreateDiagnosticFindingCommand(
                validDiagnosticId,
                "details",
                EFindingSeverity.CRITICAL,
                100
        );

        var createdDiagnostic = new Diagnostic(createDiagnosticCommand);
        createdDiagnostic.setId(validDiagnosticId);

        var createdDiagnosticFinding = new DiagnosticFinding(createFindingCommand, createdDiagnostic);
        createdDiagnosticFinding.setId(validDiagnosticFindingId);

        createdDiagnostic.setDiagnosticFindings(List.of(createdDiagnosticFinding));

        Mockito.when(diagnosticRepository.findById(validDiagnosticId))
                .thenReturn(Optional.of(createdDiagnostic));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "Fake Image Content".getBytes()
        );

        var createEvidenceCommand = new CreateEvidenceCommand(
                validDiagnosticId,
                validDiagnosticFindingId,
                mockFile,
                "comment test"
        );

        Mockito.when(fileManagementContextFacade.fetchUploadFile(Mockito.any(MultipartFile.class)))
                .thenReturn(new FileIdFeign(validFileId));

        // When
        diagnosticCommandService.handle(createEvidenceCommand);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic updatedDiagnostic = diagnosticArgumentCaptor.getValue();
        Assertions.assertThat(updatedDiagnostic).isNotNull();
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings())
                .as("Diagnostic findings")
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings().stream().findFirst().get())
                .as("Diagnostic finding")
                .isNotNull();
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings().stream().findFirst().get().getEvidences())
                .as("Diagnostic finding evidences")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("comment", "fileId")
                .containsExactly("comment test", new FileId(validFileId));
    }

    @Test
    void TestCreateDiagnosticRecommendation_ValidData_ShouldPass() {
        // Given
        var validDiagnosticId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";
        var validDiagnosticFindingId = "1a2b3c4d-5e6f-7g8h-9i0j-a1b2c3d4e5f6";

        var createDiagnosticCommand = new CreateDiagnosticCommand(
                new WorkshopId("1a2b3123123123-7g8h-9i0j-a1b2c3d4e5f6"),
                new VehicleId("123b345-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new MechanicId("1a2456456-5e6f-7g8h-9i0j-a1b2c3d4e5f6"),
                new DiagnosticDetails("details", "details", "details")
        );
        var createFindingCommand = new CreateDiagnosticFindingCommand(
                validDiagnosticId,
                "details",
                EFindingSeverity.CRITICAL,
                100
        );

        var createdDiagnostic = new Diagnostic(createDiagnosticCommand);
        createdDiagnostic.setId(validDiagnosticId);

        var createdDiagnosticFinding = new DiagnosticFinding(createFindingCommand, createdDiagnostic);
        createdDiagnosticFinding.setId(validDiagnosticFindingId);

        createdDiagnostic.setDiagnosticFindings(List.of(createdDiagnosticFinding));

        Mockito.when(diagnosticRepository.findById(validDiagnosticId))
                .thenReturn(Optional.of(createdDiagnostic));

        var createRecommendationCommand = new CreateRecommendationCommand(
                validDiagnosticId,
                validDiagnosticFindingId,
                "details recommendation"
        );

        // When
        diagnosticCommandService.handle(createRecommendationCommand);

        // Then
        Mockito.verify(diagnosticRepository).save(diagnosticArgumentCaptor.capture());
        Diagnostic updatedDiagnostic = diagnosticArgumentCaptor.getValue();
        Assertions.assertThat(updatedDiagnostic).isNotNull();
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings())
                .as("Diagnostic findings")
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings().stream().findFirst().get())
                .as("Diagnostic finding")
                .isNotNull();
        Assertions.assertThat(updatedDiagnostic.getDiagnosticFindings().stream().findFirst().get().getRecommendedActions())
                .as("Diagnostic finding recommendations")
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("content")
                .isEqualTo("details recommendation");
    }
}