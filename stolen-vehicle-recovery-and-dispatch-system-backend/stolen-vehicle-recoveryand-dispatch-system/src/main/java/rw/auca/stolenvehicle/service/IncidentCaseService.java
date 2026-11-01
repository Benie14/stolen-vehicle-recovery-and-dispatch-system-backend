package rw.auca.stolenvehicle.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.stolenvehicle.entity.*;
import rw.auca.stolenvehicle.repository.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class IncidentCaseService {

 private final IncidentCaseRepository incidentCaseRepository;
 private final RecoveryRecordRepository recoveryRecordRepository;
 private final PatrolUnitRepository patrolUnitRepository;

 public IncidentCaseService(
         IncidentCaseRepository incidentCaseRepository,
         RecoveryRecordRepository recoveryRecordRepository,
         PatrolUnitRepository patrolUnitRepository) {
     this.incidentCaseRepository=incidentCaseRepository;
     this.recoveryRecordRepository=recoveryRecordRepository;
     this.patrolUnitRepository=patrolUnitRepository;
 }

 public String createIncidentCase(IncidentCase incidentCase) {
  if (incidentCase.getReportDate()==null || incidentCase.getReportDate().isBefore(LocalDate.now()))
      return "PAST_DATE";
  if (incidentCase.getPatrolUnit()!=null &&
          incidentCaseRepository.existsByPatrolUnit_IdAndReportDateAndStatusNot
                  (incidentCase.getPatrolUnit().getId(),
                          incidentCase.getReportDate(),
                          CaseStatus.WITHDRAWN))
      return "DISPATCH_CONFLICT";

  incidentCase.setStatus(CaseStatus.REPORTED);
  incidentCaseRepository.save(incidentCase);
  return "SAVED";
 }

 public List<IncidentCase> getAllIncidentCases(){
     return incidentCaseRepository.findAll();
 }

 public Optional<IncidentCase> getIncidentCaseById(UUID incidentCaseId){
     return incidentCaseRepository.findById(incidentCaseId);
 }
 public Optional<IncidentCase> updateIncidentCase(UUID incidentCaseId, IncidentCase changes) {
     Optional<IncidentCase> result=incidentCaseRepository.findById(incidentCaseId);
     if(result.isEmpty())
         return Optional.empty();
     IncidentCase existing=result.get();
     existing.setReportDate(changes.getReportDate());
     existing.setLastKnownLocation(changes.getLastKnownLocation());
     existing.setVehicle(changes.getVehicle());
     existing.setPatrolUnit(changes.getPatrolUnit());
     return Optional.of(incidentCaseRepository.save(existing));
 }

 public String deleteIncidentCase(UUID incidentCaseId) {
     if(!incidentCaseRepository.existsById(incidentCaseId))
         return "NOT_FOUND";
     if(recoveryRecordRepository.existsByIncidentCase_Id(incidentCaseId))
         return "HAS_RECOVERY_RECORDS";
     incidentCaseRepository.deleteById(incidentCaseId);
     return "DELETED";
 }

 public List<IncidentCase> getIncidentCasesByStatus(CaseStatus status){
     return incidentCaseRepository.findByStatusOrderByReportDateAsc(status);
 }
 public List<IncidentCase> getIncidentCasesBetweenDates(LocalDate startDate, LocalDate endDate){
     return incidentCaseRepository.findByReportDateBetweenOrderByReportDateAsc(startDate,endDate);
 }
 public String updateIncidentCaseStatus(UUID incidentCaseId, CaseStatus newStatus) {
     Optional<IncidentCase> result=incidentCaseRepository.findById(incidentCaseId);
     if(result.isEmpty())
         return "NOT_FOUND";
     CaseStatus oldStatus=result.get().getStatus();
     boolean allowed=(oldStatus==CaseStatus.REPORTED&&(newStatus==CaseStatus.DISPATCHED||
             newStatus==CaseStatus.WITHDRAWN))||
             (oldStatus==CaseStatus.DISPATCHED&&(newStatus==CaseStatus.RECOVERED||
                     newStatus==CaseStatus.WITHDRAWN));
     if(!allowed)
         return "INVALID:"+oldStatus+":"+newStatus;
     result.get().setStatus(newStatus);
     incidentCaseRepository.save(result.get());
     return "UPDATED";
 }

 public List<Object[]> getIncidentCaseCountsByStatus(){
     return incidentCaseRepository.countCasesByStatus();
 }
 @Transactional
 public String cancelIncidentCasesForPatrolUnitOnDate(UUID patrolUnitId, LocalDate date) {
     if(!patrolUnitRepository.existsById(patrolUnitId))
         return "PATROL_UNIT_NOT_FOUND";
     int count=incidentCaseRepository.cancelDayCases(patrolUnitId,date,CaseStatus.WITHDRAWN,CaseStatus.RECOVERED);
     return count+" incident cases cancelled";
 }

 public boolean patrolUnitExists(UUID patrolUnitId){
     return patrolUnitRepository.existsById(patrolUnitId);
 }

 public Page<IncidentCase> getIncidentCasesPage(int page,int size,String sort) {

  String[] parts=sort.split(",",2);
  Sort.Direction direction=parts.length>1&&parts[1]
          .equalsIgnoreCase("asc")?Sort
          .Direction.ASC:Sort.Direction.DESC;
  return incidentCaseRepository.findAll(PageRequest.of(page,size,Sort.by(direction,parts[0])));
 }

 public long getIncidentCaseCountByStatus(CaseStatus status){
     return incidentCaseRepository.countByStatus(status);
 }
 public long getIncidentCaseCountToday(){
     return incidentCaseRepository
             .findByReportDateBetweenOrderByReportDateAsc(LocalDate.now()
                     ,LocalDate.now()).size();}
}
