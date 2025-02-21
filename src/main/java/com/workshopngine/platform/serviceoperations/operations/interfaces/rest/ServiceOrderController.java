package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetServiceOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateServiceOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.ServiceOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateServiceOrderCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.ServiceOrderResourceFromEntityAssembler;
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
@RequestMapping(value = "/service-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Service Orders", description = "Service Order Endpoints")
public class ServiceOrderController {
    private final ServiceOrderCommandService serviceOrderCommandService;
    private final ServiceOrderQueryService serviceOrderQueryService;

    @GetMapping("/{serviceOrderId}")
    @Operation(summary = "Get Service Order by ID", description = "Get a service order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service Order found"),
            @ApiResponse(responseCode = "404", description = "Service Order not found")
    })
    public ResponseEntity<ServiceOrderResource> getServiceOrderById(@PathVariable String serviceOrderId){
        var query = new GetServiceOrderByIdQuery(serviceOrderId);
        var serviceOrder = serviceOrderQueryService.handle(query);
        if (serviceOrder.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var serviceOrderResource = ServiceOrderResourceFromEntityAssembler.toResourceFromEntity(serviceOrder.get());
        return new ResponseEntity<>(serviceOrderResource, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Create Service Order", description = "Create a new service order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service Order created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ServiceOrderResource> createServiceOrder(@RequestBody CreateServiceOrderResource resource){
        var command = CreateServiceOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        var serviceOrderId = serviceOrderCommandService.handle(command);
        if (serviceOrderId.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(getServiceOrderById(serviceOrderId).getBody(), HttpStatus.CREATED);
    }
}
