package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.*;
import rw.auca.stolenvehicle.repository.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class RecoveryRecordService {
    private final RecoveryRecordRepository recoveryRecordRepository;
    private final IncidentCaseRepository incidentCaseRepository;

    public RecoveryRecordService(
            RecoveryRecordRepository recoveryRecordRepository,
            IncidentCaseRepository incidentCaseRepository){
        this.recoveryRecordRepository=recoveryRecordRepository;
        this.incidentCaseRepository=incidentCaseRepository;}

    public String createRecoveryRecord(UUID incidentCaseId,RecoveryRecord record){
        Optional<IncidentCase> found=incidentCaseRepository.findById(incidentCaseId);
        if(found.isEmpty())
            return "CASE_NOT_FOUND";
        if(found.get().getStatus()!=CaseStatus.RECOVERED)
            return "CASE_NOT_RECOVERED";
        record.setRecoveredDate(LocalDate.now());
        record.setIncidentCase(found.get());
        recoveryRecordRepository.save(record);
        return "SAVED";
    }

 public List<RecoveryRecord> getAllRecoveryRecords(){
        return recoveryRecordRepository.findAll();
    }
 public List<RecoveryRecord> getRecoveryRecordsByVehiclePlateNumber(String plateNumber){
        return recoveryRecordRepository.findVehicleRecoveryHistory(plateNumber);
    }
 public boolean deleteRecoveryRecord(UUID recoveryRecordId){
        if(!recoveryRecordRepository.existsById(recoveryRecordId))
            return false;
        recoveryRecordRepository.deleteById(recoveryRecordId);
        return true;
    }
 public long getRecoveryRecordCount(){
        return recoveryRecordRepository.count();
    }
}
