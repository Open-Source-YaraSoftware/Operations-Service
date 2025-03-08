package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllDiagnosticFindingsByDiagnosticIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetDiagnosticFindingByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticFindingResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticFindingResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateDiagnosticFindingCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.DiagnosticFindingResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/diagnostics/{diagnosticId}/findings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diagnostics", description = "Diagnostic Endpoints")
public class DiagnosticFindingController {
    private final DiagnosticCommandService diagnosticCommandService;
    private final DiagnosticQueryService diagnosticQueryService;

    public DiagnosticFindingController(DiagnosticCommandService diagnosticCommandService, DiagnosticQueryService diagnosticQueryService) {
        this.diagnosticCommandService = diagnosticCommandService;
        this.diagnosticQueryService = diagnosticQueryService;
    }

    @GetMapping()
    @Operation(summary = "Get all diagnostic findings by diagnostic ID", description = "Get all diagnostic findings by diagnostic ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic findings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Diagnostic not found")
    })
    public ResponseEntity<DiagnosticFindingResource[]> getAllDiagnosticsFindingById(@PathVariable String diagnosticId) {
        var query = new GetAllDiagnosticFindingsByDiagnosticIdQuery(diagnosticId);
        var diagnosticFindings = diagnosticQueryService.handle(query);
        var diagnosticFindingResources = diagnosticFindings.stream()
                .map(DiagnosticFindingResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(DiagnosticFindingResource[]::new);
        return new ResponseEntity<>(diagnosticFindingResources, HttpStatus.OK);
    }

    @GetMapping("/{findingId}")
    @Operation(summary = "Get diagnostic finding by diagnostic ID and finding ID", description = "Get diagnostic finding by diagnostic ID and finding ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic finding retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Diagnostic finding not found")
    })
    public ResponseEntity<DiagnosticFindingResource> getDiagnosticFindingById(@PathVariable String diagnosticId, @PathVariable String findingId) {
        var query = new GetDiagnosticFindingByIdQuery(diagnosticId, findingId);
        var diagnosticFinding = diagnosticQueryService.handle(query);
        if (diagnosticFinding.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var diagnosticFindingResource = DiagnosticFindingResourceFromEntityAssembler.toResourceFromEntity(diagnosticFinding.get());
        return new ResponseEntity<>(diagnosticFindingResource, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create diagnostic finding", description = "Create diagnostic finding")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diagnostic finding created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<DiagnosticFindingResource> createDiagnosticFinding(@PathVariable String diagnosticId, @RequestBody CreateDiagnosticFindingResource diagnosticFindingResource) {
        var command = CreateDiagnosticFindingCommandFromResourceAssembler.toCommandFromResource(diagnosticId, diagnosticFindingResource);
        var createdDiagnosticFinding = diagnosticCommandService.handle(command);
        if (createdDiagnosticFinding.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var createdDiagnosticFindingResource = DiagnosticFindingResourceFromEntityAssembler.toResourceFromEntity(createdDiagnosticFinding.get());
        return new ResponseEntity<>(createdDiagnosticFindingResource, HttpStatus.CREATED);
    }
}
