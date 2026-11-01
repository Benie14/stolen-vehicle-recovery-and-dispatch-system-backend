package rw.auca.stolenvehicle.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.*;
import rw.auca.stolenvehicle.service.IncidentCaseService;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/incident-cases")
public class IncidentCaseController {

 private final IncidentCaseService incidentCaseService;

 public IncidentCaseController(IncidentCaseService incidentCaseService){
     this.incidentCaseService=incidentCaseService;
 }

 @PostMapping("/save")
 public ResponseEntity<String> createIncidentCase(@RequestBody IncidentCase incidentCase){
     String r=incidentCaseService.createIncidentCase(incidentCase);
     if(r.equals("PAST_DATE"))
         return ResponseEntity
                 .badRequest()
                 .body("Incident case date cannot be in the past");
     if(r.equals("DISPATCH_CONFLICT"))
         return ResponseEntity
                 .status(409)
                 .body("Patrol unit is already dispatched on that date");
     return ResponseEntity
             .status(201)
             .body("Incident case saved successfully");
 }

 @GetMapping("/all")
 public List<IncidentCase> getAllIncidentCases(){
     return incidentCaseService.getAllIncidentCases();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> getIncidentCaseById(@PathVariable UUID id){
     Optional<IncidentCase> x=incidentCaseService.getIncidentCaseById(id);
     if(x.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("The incident case with that id does not exist");
     return ResponseEntity.ok(x.get());
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateIncidentCase(
         @PathVariable UUID id,
         @RequestBody IncidentCase x){
     Optional<IncidentCase> r=incidentCaseService.updateIncidentCase(id,x);
     if(r.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("The incident case with that id does not exist");
     return ResponseEntity.ok(r.get());
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deleteIncidentCase(@PathVariable UUID id){
     String r=incidentCaseService.deleteIncidentCase(id);
     if(r.equals("NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The incident case with that id does not exist");
     if(r.equals("HAS_RECOVERY_RECORDS"))
         return ResponseEntity
                 .status(409)
                 .body("That incident case has recovery records, delete them first");
     return ResponseEntity.ok("Incident case deleted successfully");
 }

 @GetMapping("/by-status")
 public List<IncidentCase> getIncidentCasesByStatus(@RequestParam CaseStatus status){
     return incidentCaseService.getIncidentCasesByStatus(status);
 }

 @GetMapping("/between")
 public List<IncidentCase> getIncidentCasesBetweenDates(
         @RequestParam String start,
         @RequestParam String end){
     return incidentCaseService.getIncidentCasesBetweenDates(LocalDate.parse(start),LocalDate.parse(end));
 }

 @PatchMapping("/{id}/status")
 public ResponseEntity<String> updateIncidentCaseStatus(
         @PathVariable UUID id,
         @RequestParam CaseStatus status){
     String r=incidentCaseService.updateIncidentCaseStatus(id,status);
     if(r.equals("NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The incident case with that id does not exist");
     if(r.startsWith("INVALID:")){
         String[] p=r.split(":");
         return ResponseEntity
                 .badRequest()
                 .body("Cannot change status from "+p[1]+" to "+p[2]);
     }return ResponseEntity.ok("Incident case status updated successfully");
 }

 @GetMapping("/stats/by-status")
 public List<Object[]> getIncidentCaseCountsByStatus(){
     return incidentCaseService.getIncidentCaseCountsByStatus();
 }

 @PatchMapping("/cancel-day")
 public ResponseEntity<String> cancelIncidentCasesForPatrolUnitOnDate(
         @RequestParam UUID patrolUnitId,
         @RequestParam String date){
     String r=incidentCaseService.cancelIncidentCasesForPatrolUnitOnDate(patrolUnitId,LocalDate.parse(date));
     if(r.equals("PATROL_UNIT_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     return ResponseEntity.ok(r);
 }

 @GetMapping("/page")
 public Page<IncidentCase> getIncidentCasesPage(
         @RequestParam(defaultValue="0") int page,
         @RequestParam(defaultValue="5") int size,
         @RequestParam(defaultValue="reportDate,desc") String sort){
     return incidentCaseService.getIncidentCasesPage(page,size,sort);
 }
}
