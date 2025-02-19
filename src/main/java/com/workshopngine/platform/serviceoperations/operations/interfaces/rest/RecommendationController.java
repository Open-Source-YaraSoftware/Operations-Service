package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllRecommendationByDiagnosticFindingIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.DiagnosticQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateRecommendationResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.RecommendationResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateRecommendationCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.RecommendationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/diagnostics/{diagnosticId}/findings/{findingId}/recommendation", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Diagnostics", description = "Diagnostic Endpoints")
public class RecommendationController {
    private final DiagnosticCommandService diagnosticCommandService;
    private final DiagnosticQueryService diagnosticQueryService;

    public RecommendationController(DiagnosticCommandService diagnosticCommandService, DiagnosticQueryService diagnosticQueryService) {
        this.diagnosticCommandService = diagnosticCommandService;
        this.diagnosticQueryService = diagnosticQueryService;
    }

    @GetMapping()
    @Operation(summary = "Get all diagnostic recommendations by diagnostic ID and finding ID", description = "Get all diagnostic recommendations by diagnostic ID and finding ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic recommendations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Diagnostic not found")
    })
    public ResponseEntity<RecommendationResource[]> getAllDiagnosticsRecommendationsById(
            @PathVariable String diagnosticId,
            @PathVariable String findingId
    ) {
        var query = new GetAllRecommendationByDiagnosticFindingIdQuery(diagnosticId, findingId);
        var diagnosticRecommendations = diagnosticQueryService.handle(query);
        var diagnosticRecommendationResources = diagnosticRecommendations.stream()
                .map(RecommendationResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(RecommendationResource[]::new);
        return new ResponseEntity<>(diagnosticRecommendationResources, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create Diagnostic Recommendation", description = "Create a new diagnostic recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diagnostic recommendation created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<RecommendationResource> createDiagnosticRecommendation(
            @PathVariable String diagnosticId,
            @PathVariable String findingId,
            @RequestBody CreateRecommendationResource recommendationResource
    ) {
        var command = CreateRecommendationCommandFromResourceAssembler.toCommandFromResource(diagnosticId, findingId, recommendationResource);
        var createdRecommendation = diagnosticCommandService.handle(command);
        if (createdRecommendation.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var createdRecommendationResource = RecommendationResourceFromEntityAssembler.toResourceFromEntity(createdRecommendation.get());
        return new ResponseEntity<>(createdRecommendationResource, HttpStatus.CREATED);
    }
}
