package com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EDiagnosticType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.MechanicId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiagnosticRepositoryIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @Test
    void TestFindAllDiagnostics_ValidWorkshopId_ShouldPass() {
        // Given
        var workshopId = new WorkshopId("1");
        var command = new CreateDiagnosticCommand(
                workshopId,
                new VehicleId("9bc16276-ad51-4216-a0f7-2aef0668c5c5"),
                new MechanicId("1"),
                EDiagnosticType.PREVENTIVE,
                "desired outcome",
                "details"
        );
        var diagnostic = new Diagnostic(command);
        diagnosticRepository.save(diagnostic);

        // When
        var diagnostics = diagnosticRepository.findAllByWorkshopId(workshopId);

        // Then
        Assertions.assertThat(diagnostics).isNotEmpty();
        Assertions.assertThat(diagnostics.size()).isEqualTo(1);
    }
}