package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.MediaAttachment;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.*;

import java.util.Collection;
import java.util.Optional;

public interface DiagnosticQueryService {
    Optional<Diagnostic> handle(GetDiagnosticByIdQuery query);
    Collection<Diagnostic> handle(GetAllDiagnosticsByWorkshopIdQuery query);
    Collection<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query);
    Optional<DiagnosticFinding> handle(GetDiagnosticFindingByIdQuery query);
    Collection<DiagnosticFinding> handle(GetAllDiagnosticFindingsByDiagnosticIdQuery query);
    Collection<MediaAttachment> handle(GetAllAttachmentByDiagnosticFindingIdQuery query);
}
