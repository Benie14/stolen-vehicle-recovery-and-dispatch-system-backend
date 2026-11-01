package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.stolenvehicle.entity.Capability;
import java.util.List;
import java.util.UUID;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, UUID> {
    @Query
            ("SELECT c FROM Capability c WHERE c.patrolUnits IS EMPTY")
    List<Capability> findUnusedCapabilities();
}
