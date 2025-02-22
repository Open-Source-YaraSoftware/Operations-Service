package com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, String> {
}
