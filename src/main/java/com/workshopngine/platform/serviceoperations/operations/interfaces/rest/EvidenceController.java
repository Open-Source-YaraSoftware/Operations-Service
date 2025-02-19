package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllEvidencesByDiagnosticFindingIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateEvidenceResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.EvidenceResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateDiagnosticEvidenceCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/diagnostics/{diagnosticId}/findings/{findingId}/evidences", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diagnostics", description = "Diagnostic Endpoints")
public class EvidenceController {
    private final DiagnosticCommandService diagnosticCommandService;
    private final DiagnosticQueryService diagnosticQueryService;

    public EvidenceController(DiagnosticCommandService diagnosticCommandService, DiagnosticQueryService diagnosticQueryService) {
        this.diagnosticCommandService = diagnosticCommandService;
        this.diagnosticQueryService = diagnosticQueryService;
    }

    @GetMapping()
    @Operation(summary = "Get all diagnostic evidences by diagnostic ID and finding ID", description = "Get all diagnostic evidences by diagnostic ID and finding ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic evidences retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Diagnostic not found")
    })
    public ResponseEntity<EvidenceResource[]> getAllDiagnosticsEvidencesById(
            @PathVariable String diagnosticId,
            @PathVariable String findingId
    ) {
        var query = new GetAllEvidencesByDiagnosticFindingIdQuery(diagnosticId, findingId);
        var diagnosticEvidences = diagnosticQueryService.handle(query);
        var diagnosticEvidenceResources = diagnosticEvidences.stream()
                .map(EvidenceResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(EvidenceResource[]::new);
        return new ResponseEntity<>(diagnosticEvidenceResources, HttpStatus.OK);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create Diagnostic Evidence", description = "Create a new diagnostic evidence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diagnostic evidence created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<EvidenceResource> createDiagnosticEvidence(
            @PathVariable String diagnosticId,
            @PathVariable String findingId,
            @RequestPart("resource") CreateEvidenceResource resource,
            @RequestPart("file") MultipartFile file
    ){
        var command = CreateDiagnosticEvidenceCommandFromResourceAssembler.toCommandFromResource(diagnosticId, findingId, file, resource);
        var evidence = diagnosticCommandService.handle(command);
        if (evidence.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var evidenceResource = EvidenceResourceFromEntityAssembler.toResourceFromEntity(evidence.get());
        return new ResponseEntity<>(evidenceResource, HttpStatus.CREATED);
    }
}
