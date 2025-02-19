package com.workshopngine.platform.serviceoperations.operations.infrastructure.persistence.jpa.repositories;

import com.workshopngine.platform.serviceoperations.operations.domain.model.aggregates.Diagnostic;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.VehicleId;
import com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects.WorkshopId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface DiagnosticRepository extends JpaRepository<Diagnostic, String> {
    Collection<Diagnostic> findAllByWorkshopId(WorkshopId workshopId);
    Collection<Diagnostic> findAllByVehicleId(VehicleId vehicleId);
}
