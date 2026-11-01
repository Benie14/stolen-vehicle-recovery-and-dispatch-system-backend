package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.stolenvehicle.entity.RecoveryRecord;
import java.util.List;
import java.util.UUID;

@Repository
public interface RecoveryRecordRepository extends JpaRepository<RecoveryRecord, UUID> {
    @Query
            ("SELECT r FROM RecoveryRecord r " +
                    "WHERE r.incidentCase.vehicle.plateNumber = :plateNumber ORDER BY r.recoveredDate DESC")

    List<RecoveryRecord> findVehicleRecoveryHistory(
            @Param("plateNumber") String plateNumber);

    boolean existsByIncidentCase_Id(UUID incidentCaseId);
    long count();
}
