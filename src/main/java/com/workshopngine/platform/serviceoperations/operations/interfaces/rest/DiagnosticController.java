package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetDiagnosticByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateDiagnosticResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.DiagnosticResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateDiagnosticCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.DiagnosticResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/diagnostics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diagnostics", description = "Diagnostic Endpoints")
public class DiagnosticController {
    private final DiagnosticCommandService diagnosticCommandService;
    private final DiagnosticQueryService diagnosticQueryService;

    public DiagnosticController(DiagnosticCommandService diagnosticCommandService, DiagnosticQueryService diagnosticQueryService) {
        this.diagnosticCommandService = diagnosticCommandService;
        this.diagnosticQueryService = diagnosticQueryService;
    }

    @GetMapping("/{diagnosticId}")
    @Operation(summary = "Get Diagnostic by ID", description = "Get a diagnostic by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic found"),
            @ApiResponse(responseCode = "404", description = "Diagnostic not found")
    })
    public ResponseEntity<DiagnosticResource> getDiagnosticById(@PathVariable String diagnosticId){
        var query = new GetDiagnosticByIdQuery(diagnosticId);
        var diagnostic = diagnosticQueryService.handle(query);
        if (diagnostic.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var diagnosticResource = DiagnosticResourceFromEntityAssembler.toResourceFromEntity(diagnostic.get());
        return new ResponseEntity<>(diagnosticResource, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create Diagnostic", description = "Create a new diagnostic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diagnostic created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<DiagnosticResource> createDiagnostic(@RequestBody CreateDiagnosticResource resource){
        var command = CreateDiagnosticCommandFromResourceAssembler.toCommandFromResource(resource);
        var diagnosticId = diagnosticCommandService.handle(command);
        if (diagnosticId.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(getDiagnosticById(diagnosticId).getBody(), HttpStatus.CREATED);
    }
}
