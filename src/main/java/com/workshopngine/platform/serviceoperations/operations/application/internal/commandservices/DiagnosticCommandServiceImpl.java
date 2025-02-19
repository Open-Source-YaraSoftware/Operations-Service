package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.application.internal.outboundservices.acl.FileManagementContextFacade;
import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Evidence;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.DiagnosticRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiagnosticCommandServiceImpl implements DiagnosticCommandService {
    private final DiagnosticRepository diagnosticRepository;
    private final FileManagementContextFacade fileManagementContextFacade;

    public DiagnosticCommandServiceImpl(DiagnosticRepository diagnosticRepository, FileManagementContextFacade fileManagementContextFacade) {
        this.diagnosticRepository = diagnosticRepository;
        this.fileManagementContextFacade = fileManagementContextFacade;
    }

    @Override
    public String handle(CreateDiagnosticCommand command) {
        var diagnostic = new Diagnostic(command);
        try {
            diagnosticRepository.save(diagnostic);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving diagnostic: " + e.getMessage());
        }
        return diagnostic.getId();
    }

    @Override
    public Optional<DiagnosticFinding> handle(CreateDiagnosticFindingCommand command) {
        var diagnostic = diagnosticRepository.findById(command.diagnosticId());
        if (diagnostic.isEmpty()) throw new IllegalArgumentException("Diagnostic with ID %s not found".formatted(command.diagnosticId()));
        var diagnosticFinding = diagnostic.get().addDiagnosticFinding(command);
        try {
            diagnosticRepository.save(diagnostic.get());
            return Optional.of(diagnosticFinding);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving diagnostic finding: " + e.getMessage());
        }
    }

    @Override
    public Optional<Evidence> handle(CreateEvidenceCommand command) {
        var diagnostic = diagnosticRepository.findById(command.diagnosticId());
        if (diagnostic.isEmpty()) throw new IllegalArgumentException("Diagnostic with ID %s not found".formatted(command.diagnosticId()));
        var fileId = fileManagementContextFacade.fetchUploadFile(command.file());
        if (fileId.id() == null) throw new IllegalArgumentException("Error while uploading file");
        var evidence = diagnostic.get().addEvidence(command, new FileId(fileId.id()));
        try {
            diagnosticRepository.save(diagnostic.get());
            return Optional.of(evidence);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving evidence: " + e.getMessage());
        }
    }

    @Override
    public Optional<Recommendation> handle(CreateRecommendationCommand command) {
        var diagnostic = diagnosticRepository.findById(command.diagnosticId());
        if (diagnostic.isEmpty()) throw new IllegalArgumentException("Diagnostic with ID %s not found".formatted(command.diagnosticId()));
        var recommendation = diagnostic.get().addRecommendation(command);
        try {
            diagnosticRepository.save(diagnostic.get());
            return Optional.of(recommendation);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving recommendation: " + e.getMessage());
        }
    }
}
