package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetWorkOrderByIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderCommandService;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.CreateWorkOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.CreateWorkOrderCommandFromResourceAssembler;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.WorkOrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/work-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Work Orders", description = "Work Order Endpoints")
public class WorkOrderController {
    private final WorkOrderCommandService workOrderCommandService;
    private final WorkOrderQueryService workOrderQueryService;

    public WorkOrderController(WorkOrderCommandService workOrderCommandService, WorkOrderQueryService workOrderQueryService) {
        this.workOrderCommandService = workOrderCommandService;
        this.workOrderQueryService = workOrderQueryService;
    }

    @GetMapping("/{workOrderId}")
    @Operation(summary = "Get Work Order by ID", description = "Get a work order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work Order found"),
            @ApiResponse(responseCode = "404", description = "Work Order not found")
    })
    public ResponseEntity<WorkOrderResource> getWorkOrderById(@PathVariable String workOrderId){
        var query = new GetWorkOrderByIdQuery(workOrderId);
        var workOrder = workOrderQueryService.handle(query);
        if (workOrder.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var workOrderResource = WorkOrderResourceFromEntityAssembler.toResourceFromEntity(workOrder.get());
        return new ResponseEntity<>(workOrderResource, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create Work Order", description = "Create a new work order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work Order created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<WorkOrderResource> createWorkOrder(@RequestBody CreateWorkOrderResource resource) {
        var command = CreateWorkOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        var workOrderId = workOrderCommandService.handle(command);
        if (workOrderId.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(getWorkOrderById(workOrderId).getBody(), HttpStatus.CREATED);
    }
}
