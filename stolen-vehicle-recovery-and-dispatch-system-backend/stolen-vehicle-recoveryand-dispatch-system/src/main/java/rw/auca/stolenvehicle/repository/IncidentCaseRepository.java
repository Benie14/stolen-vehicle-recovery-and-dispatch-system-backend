package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.stolenvehicle.entity.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface IncidentCaseRepository extends JpaRepository<IncidentCase,UUID>{

    List<IncidentCase> findByStatusOrderByReportDateAsc(CaseStatus status);
    List<IncidentCase> findByReportDateBetweenOrderByReportDateAsc(LocalDate start,LocalDate end);
    boolean existsByPatrolUnit_IdAndReportDateAndStatusNot(UUID patrolUnitId,LocalDate reportDate,CaseStatus status);
    boolean existsByVehicle_Id(UUID vehicleId);

    @Query
            ("SELECT DISTINCT i.vehicle FROM IncidentCase i WHERE i.patrolUnit.id=:patrolUnitId")

    List<Vehicle> findVehiclesByPatrolUnitId(@Param("patrolUnitId") UUID patrolUnitId);

    @Query
            ("SELECT i.status, COUNT(i) FROM IncidentCase i GROUP BY i.status")

    List<Object[]> countCasesByStatus();

    @Modifying
    @Transactional
    @Query
            ("UPDATE IncidentCase i SET i.status=:withdrawn WHERE " +
                    "i.patrolUnit.id=:patrolUnitId AND i.reportDate=:date AND i.status<>:recovered")

    int cancelDayCases(
            @Param("patrolUnitId") UUID patrolUnitId,
            @Param("date") LocalDate date,
            @Param("withdrawn") CaseStatus withdrawn,
            @Param("recovered") CaseStatus recovered);

 long countByStatus(CaseStatus status);
}
