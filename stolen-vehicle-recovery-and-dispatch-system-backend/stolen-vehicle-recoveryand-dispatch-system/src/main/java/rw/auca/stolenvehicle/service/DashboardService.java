package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.CaseStatus;
import rw.auca.stolenvehicle.repository.*;
import java.util.*;

@Service
public class DashboardService {

 private final VehicleRepository vehicleRepository;
 private final PatrolUnitRepository patrolUnitRepository;
 private final SectorRepository sectorRepository;
 private final IncidentCaseService incidentCaseService;
 private final RecoveryRecordService recoveryRecordService;

 public DashboardService(
         VehicleRepository v,
         PatrolUnitRepository p,
         SectorRepository s,
         IncidentCaseService i,
         RecoveryRecordService r){
     vehicleRepository=v;
     patrolUnitRepository=p;
     sectorRepository=s;
     incidentCaseService=i;
     recoveryRecordService=r;
 }

 public Map<String,Long> summary(){
     Map<String,Long> m=new LinkedHashMap<>();
     m.put("vehicles",vehicleRepository.count());
     m.put("patrolUnits",patrolUnitRepository.count());
     m.put("freeSectors",(long)sectorRepository.findFreeSectors().size());
     m.put("casesToday",incidentCaseService.getIncidentCaseCountToday());
     m.put("reported",incidentCaseService.getIncidentCaseCountByStatus(CaseStatus.REPORTED));
     m.put("recoveryRecords",recoveryRecordService.getRecoveryRecordCount());return m;
 }
}
