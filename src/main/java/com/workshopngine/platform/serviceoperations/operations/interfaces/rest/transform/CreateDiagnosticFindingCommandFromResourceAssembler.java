package com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateDiagnosticFindingCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EFindingSeverity;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ESolutionType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ProposedSolution;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticFindingResource;

public class CreateDiagnosticFindingCommandFromResourceAssembler {
    public static CreateDiagnosticFindingCommand toCommandFromResource(String diagnosticId, CreateDiagnosticFindingResource resource) {
        return new CreateDiagnosticFindingCommand(
                diagnosticId,
                resource.description(),
                EFindingSeverity.valueOf(resource.severity()),
                new ProposedSolution(resource.solutionDescription(), ESolutionType.fromString(resource.solutionType())),
                resource.estimatedRepairCost(),
                resource.remarks()
        );
    }
}
