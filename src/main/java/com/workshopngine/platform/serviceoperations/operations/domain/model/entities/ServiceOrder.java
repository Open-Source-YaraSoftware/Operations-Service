package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EServiceStatus;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.EServiceType;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.ServiceId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServiceOrder {
    @EmbeddedId
    private ServiceId serviceId;

    @Enumerated(EnumType.STRING)
    private EServiceType type;

    @Enumerated(EnumType.STRING)
    private EServiceStatus status;

    @ManyToOne
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;
}
