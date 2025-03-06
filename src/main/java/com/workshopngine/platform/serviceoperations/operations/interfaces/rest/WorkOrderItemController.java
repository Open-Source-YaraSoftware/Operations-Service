package com.workshopngine.platform.serviceoperations.operations.interfaces.rest;

import com.workshopngine.platform.serviceoperations.operations.domain.model.queries.GetAllWorkOrderItemByWorkOrderIdQuery;
import com.workshopngine.platform.serviceoperations.operations.domain.services.WorkOrderQueryService;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.dto.WorkOrderItemResource;
import com.workshopngine.platform.serviceoperations.operations.interfaces.rest.transform.WorkOrderItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/work-orders/{workOrderId}/items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Work Orders", description = "Work Order Endpoints")
public class WorkOrderItemController {
    private final WorkOrderQueryService workOrderQueryService;

    @GetMapping
    @Operation(summary = "Get Work Order Items by Work Order ID", description = "Get all work order items by work order ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Work Order Items found"),
                    @ApiResponse(responseCode = "404", description = "Work Order Items not found")
            }
    )
    public ResponseEntity<WorkOrderItemResource[]> getAllWorkOrderItemByWorkOrderId(@PathVariable String workOrderId) {
        var query = new GetAllWorkOrderItemByWorkOrderIdQuery(workOrderId);
        var workOrderItems = workOrderQueryService.handle(query);
        if (workOrderItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var workOrderItemsResource = workOrderItems.stream()
                .map(WorkOrderItemResourceFromEntityAssembler::toResourceFromEntity)
                .toArray(WorkOrderItemResource[]::new);
        return new ResponseEntity<>(workOrderItemsResource, HttpStatus.OK);
    }
}
