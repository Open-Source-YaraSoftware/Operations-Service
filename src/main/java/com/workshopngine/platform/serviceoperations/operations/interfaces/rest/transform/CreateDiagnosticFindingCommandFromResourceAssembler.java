package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.entities.Recommendation;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.FileId;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticFindingResource;

public class CreateDiagnosticFindingCommandFromResourceAssembler {
    public static CreateDiagnosticFindingCommand toCommandFromResource(String diagnosticId, CreateDiagnosticFindingResource resource) {
        return new CreateDiagnosticFindingCommand(
                diagnosticId,
                resource.description(),
                EFindingSeverity.fromString(resource.severity()),
                resource.estimatedRepairCost()
        );
    }
}
