package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EServiceType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EWorkOrderItemStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ServiceId;
import com.workshopngine.platform.serviceoperations.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@Entity
public class WorkOrderItem extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private ServiceId serviceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EServiceType serviceType;

    @Enumerated(EnumType.STRING)
    private EWorkOrderItemStatus itemStatus;

    private String followUpFromItemId;

    @ManyToOne
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    public WorkOrderItem() {
        super();
        this.itemStatus = EWorkOrderItemStatus.PENDING;
        this.followUpFromItemId = Strings.EMPTY;
    }

    public WorkOrderItem(WorkOrder workOrder,  CreateWorkOrderCommand command) {
        this();
        this.workOrder = workOrder;
        this.serviceType = command.serviceType();
    }
}
