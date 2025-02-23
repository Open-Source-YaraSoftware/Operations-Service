package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllExecutedStepsByExecutedProcedureIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetExecutedStepByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedStepResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedStepResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateExecutedStepCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.ExecutedStepResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/service-orders/{serviceOrderId}/executed-procedures/{executedProcedureId}/executed-steps", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Service Orders", description = "Service Order Endpoints")
public class ExecutedStepController {
    private final ServiceOrderCommandService serviceOrderCommandService;
    private final ServiceOrderQueryService serviceOrderQueryService;

    @GetMapping("/{executedStepId}")
    @Operation(summary = "Get Executed Step by ID", description = "Get an executed step by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Executed Step found"),
            @ApiResponse(responseCode = "404", description = "Executed Step not found")
    })
    public ResponseEntity<ExecutedStepResource> getExecutedStepById(@PathVariable String serviceOrderId, @PathVariable String executedProcedureId, @PathVariable String executedStepId){
        var query = new GetExecutedStepByIdQuery(serviceOrderId, executedProcedureId, executedStepId);
        var executedStep = serviceOrderQueryService.handle(query);
        if (executedStep.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var executedStepResource = ExecutedStepResourceFromEntityAssembler.toResourceFromEntity(executedStep.get());
        return new ResponseEntity<>(executedStepResource, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get Executed Steps", description = "Get all executed steps")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Executed Steps found"),
            @ApiResponse(responseCode = "404", description = "Executed Steps not found")
    })
    public ResponseEntity<ExecutedStepResource[]> getExecutedSteps(@PathVariable String serviceOrderId, @PathVariable String executedProcedureId){
        var query = new GetAllExecutedStepsByExecutedProcedureIdQuery(serviceOrderId, executedProcedureId);
        var executedSteps = serviceOrderQueryService.handle(query);
        var executedStepResources = executedSteps.stream()
                .map(ExecutedStepResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(ExecutedStepResource[]::new);
        return new ResponseEntity<>(executedStepResources, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create Executed Step", description = "Create a new executed step")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Executed Step created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<ExecutedStepResource> createExecutedStep(@PathVariable String serviceOrderId, @PathVariable String executedProcedureId, @RequestBody CreateExecutedStepResource resource){
        var command = CreateExecutedStepCommandFromResourceAssembler.toCommandFromResource(serviceOrderId, executedProcedureId, resource);
        var executedStep = serviceOrderCommandService.handle(command);
        if (executedStep.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var executedStepResource = ExecutedStepResourceFromEntityAssembler.toResourceFromEntity(executedStep.get());
        return new ResponseEntity<>(executedStepResource, HttpStatus.CREATED);
    }
}
