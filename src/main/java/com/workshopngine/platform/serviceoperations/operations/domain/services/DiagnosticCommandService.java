package com.workshopngine.platform.serviceoperations.operations.domain.services;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateEvidenceCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.DiagnosticFinding;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Evidence;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;

import java.util.Optional;

public interface DiagnosticCommandService {
    String handle(CreateDiagnosticCommand command);
    Optional<DiagnosticFinding> handle(CreateDiagnosticFindingCommand command);
    Optional<Evidence> handle(CreateEvidenceCommand command);
    Optional<Recommendation> handle(CreateRecommendationCommand command);
}
