package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.stolenvehicle.entity.Vehicle;
import java.util.List;
import java.util.UUID;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    List<Vehicle> findByMakeIgnoreCaseOrderByModelAsc(String make);
}
