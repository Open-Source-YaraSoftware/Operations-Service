package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllExecutedProceduresByServiceOrderIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetExecutedProcedureByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateExecutedProcedureResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ExecutedProcedureResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateExecutedProcedureCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.ExecutedProcedureResourceFromEntityAssembler;
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
@RequestMapping(value = "/service-orders/{serviceOrderId}/executed-procedures", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Service Orders", description = "Service Order Endpoints")
public class ExecutedProcedureController {
    private final ServiceOrderCommandService serviceOrderCommandService;
    private final ServiceOrderQueryService serviceOrderQueryService;

    @GetMapping("/{executedProcedureId}")
    @Operation(summary = "Get Executed Procedure by ID", description = "Get an executed procedure by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Executed Procedure found"),
            @ApiResponse(responseCode = "404", description = "Executed Procedure not found")
    })
    public ResponseEntity<ExecutedProcedureResource> getExecutedProcedureById(@PathVariable String serviceOrderId, @PathVariable String executedProcedureId){
        var query = new GetExecutedProcedureByIdQuery(serviceOrderId, executedProcedureId);
        var executedProcedure = serviceOrderQueryService.handle(query);
        if (executedProcedure.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var executedProcedureResource = ExecutedProcedureResourceFromEntityAssembler.toResourceFromEntity(executedProcedure.get());
        return new ResponseEntity<>(executedProcedureResource, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get Executed Procedures", description = "Get all executed procedures")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Executed Procedures found"),
            @ApiResponse(responseCode = "404", description = "Executed Procedures not found")
    })
    public ResponseEntity<ExecutedProcedureResource[]> getExecutedProcedures(@PathVariable String serviceOrderId){
        var query = new GetAllExecutedProceduresByServiceOrderIdQuery(serviceOrderId);
        var executedProcedures = serviceOrderQueryService.handle(query);
        var executedProcedureResources = executedProcedures.stream()
                .map(ExecutedProcedureResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(ExecutedProcedureResource[]::new);
        return new ResponseEntity<>(executedProcedureResources, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create Executed Procedure", description = "Create a new executed procedure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Executed Procedure created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ExecutedProcedureResource> createExecutedProcedure(@PathVariable String serviceOrderId, @RequestBody CreateExecutedProcedureResource resource){
        var command = CreateExecutedProcedureCommandFromResourceAssembler.toCommandFromResource(resource);
        var executedProcedure = serviceOrderCommandService.handle(command);
        if (executedProcedure.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var executedProcedureResource = ExecutedProcedureResourceFromEntityAssembler.toResourceFromEntity(executedProcedure.get());
        return new ResponseEntity<>(executedProcedureResource, HttpStatus.CREATED);
    }
}
