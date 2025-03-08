package com.workshopngine.platform.serviceoperations.operations.application.internal.queryservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.MediaAttachment;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.*;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticQueryService;
import com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories.DiagnosticRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosticQueryServiceImpl implements DiagnosticQueryService {
    private final DiagnosticRepository diagnosticRepository;

    public DiagnosticQueryServiceImpl(DiagnosticRepository diagnosticRepository) {
        this.diagnosticRepository = diagnosticRepository;
    }

    @Override
    public Optional<Diagnostic> handle(GetDiagnosticByIdQuery query) {
        return diagnosticRepository.findById(query.diagnosticId());
    }

    @Override
    public Collection<Diagnostic> handle(GetAllDiagnosticsByWorkshopIdQuery query) {
        return diagnosticRepository.findAllByWorkshopId(query.workshopId());
    }

    @Override
    public Collection<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query) {
        return diagnosticRepository.findAllByVehicleId(query.vehicleId());
    }

    @Override
    public Optional<DiagnosticFinding> handle(GetDiagnosticFindingByIdQuery query) {
        var diagnostic = diagnosticRepository.findById(query.diagnosticId());
        if (diagnostic.isEmpty()) throw new IllegalArgumentException("Diagnostic with ID %s not found".formatted(query.diagnosticId()));
        return Optional.of(diagnostic.get().getFindingById(query.diagnosticFindingId()));
    }

    @Override
    public Collection<DiagnosticFinding> handle(GetAllDiagnosticFindingsByDiagnosticIdQuery query) {
        var diagnostic = diagnosticRepository.findById(query.diagnosticId());
        return diagnostic.map(Diagnostic::getFindings).orElse(List.of());
    }

    @Override
    public Collection<MediaAttachment> handle(GetAllAttachmentByDiagnosticFindingIdQuery query) {
        var diagnosticFinding = getDiagnosticFindingById(query.diagnosticId(), query.diagnosticFindingId());
        return diagnosticFinding.getAttachments();
    }

    private DiagnosticFinding getDiagnosticFindingById(String diagnosticId, String diagnosticFindingId) {
        var diagnostic = diagnosticRepository.findById(diagnosticId);
        if (diagnostic.isEmpty()) throw new IllegalArgumentException("Diagnostic with ID %s not found".formatted(diagnosticId));
        var diagnosticFinding = diagnostic.get().getFindingById(diagnosticFindingId);
        if (diagnosticFinding == null) throw new IllegalArgumentException("Diagnostic finding with ID %s not found".formatted(diagnosticFindingId));
        return diagnosticFinding;
    }
}
