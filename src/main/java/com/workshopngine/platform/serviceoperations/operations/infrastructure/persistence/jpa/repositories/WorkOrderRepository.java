package com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
}
