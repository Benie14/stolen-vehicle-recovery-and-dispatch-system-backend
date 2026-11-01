package rw.auca.stolenvehicle.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.RecoveryRecord;
import rw.auca.stolenvehicle.service.RecoveryRecordService;
import java.util.*;

@RestController
@RequestMapping("/api/recovery-records")
public class RecoveryRecordController {

 private final RecoveryRecordService recoveryRecordService;

 public RecoveryRecordController(RecoveryRecordService recoveryRecordService){
     this.recoveryRecordService=recoveryRecordService;
 }

 @PostMapping("/save")
 public ResponseEntity<String> createRecoveryRecord(
         @RequestParam UUID incidentCaseId,
         @RequestBody RecoveryRecord record){
     String r=recoveryRecordService.createRecoveryRecord(incidentCaseId,record);
     if(r.equals("CASE_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The incident case with that id does not exist");
     if(r.equals("CASE_NOT_RECOVERED"))
         return ResponseEntity
                 .badRequest()
                 .body("Recovery records are only for RECOVERED incident cases");
     return ResponseEntity
             .status(201)
             .body("Recovery record saved successfully");
 }

 @GetMapping("/all")
 public List<RecoveryRecord> getAllRecoveryRecords(){
     return recoveryRecordService.getAllRecoveryRecords();
 }

 @GetMapping("/vehicle/{plateNumber}")
 public List<RecoveryRecord> getRecoveryRecordsByVehiclePlateNumber(@PathVariable String plateNumber){
     return recoveryRecordService.getRecoveryRecordsByVehiclePlateNumber(plateNumber);
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deleteRecoveryRecord(@PathVariable UUID id){
     if(!recoveryRecordService.deleteRecoveryRecord(id))
         return ResponseEntity
                 .status(404)
                 .body("The recovery record with that id does not exist");
     return ResponseEntity
             .ok("Recovery record deleted successfully");
 }
}
