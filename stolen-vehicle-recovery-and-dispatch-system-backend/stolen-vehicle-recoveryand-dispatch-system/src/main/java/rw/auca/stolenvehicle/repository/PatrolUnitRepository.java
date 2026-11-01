package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.stolenvehicle.entity.PatrolUnit;
import java.util.*;

@Repository
public interface PatrolUnitRepository extends JpaRepository<PatrolUnit,UUID>{
 @Query
         ("SELECT DISTINCT p FROM PatrolUnit p JOIN p.capabilities c WHERE LOWER(c.name)=LOWER(:name)")
 List<PatrolUnit> findByCapabilityNameIgnoreCase(
         @Param("name") String name);
 boolean existsBySector_Id(UUID sectorId);
}
