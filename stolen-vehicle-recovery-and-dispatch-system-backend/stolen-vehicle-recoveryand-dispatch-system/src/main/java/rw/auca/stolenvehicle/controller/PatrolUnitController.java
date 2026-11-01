package rw.auca.stolenvehicle.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.PatrolUnit;
import rw.auca.stolenvehicle.service.PatrolUnitService;
import java.util.*;

@RestController
@RequestMapping("/api/patrol-units")
public class PatrolUnitController {

 private final PatrolUnitService patrolUnitService;

 public PatrolUnitController(PatrolUnitService patrolUnitService){
     this.patrolUnitService=patrolUnitService;
 }

 @PostMapping("/save")
 public ResponseEntity<String> createPatrolUnit(@RequestBody PatrolUnit x){
     patrolUnitService.createPatrolUnit(x);
     return ResponseEntity
             .status(201)
             .body("Patrol unit saved successfully");
 }

 @GetMapping("/all")
 public List<PatrolUnit> getAllPatrolUnits(){
     return patrolUnitService.getAllPatrolUnits();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> getPatrolUnitById(@PathVariable UUID id){
     Optional<PatrolUnit>x=patrolUnitService.getPatrolUnitById(id);
     if(x.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     return ResponseEntity.ok(x.get());
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updatePatrolUnit(
         @PathVariable UUID id,
         @RequestBody PatrolUnit x){
     Optional<PatrolUnit>r=patrolUnitService.updatePatrolUnit(id,x);
     if(r.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     return ResponseEntity.ok(r.get());
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deletePatrolUnit(@PathVariable UUID id){
     return patrolUnitService.deletePatrolUnit(id)?ResponseEntity.ok("Patrol unit deleted successfully"):
             ResponseEntity
                     .status(404)
                     .body("The patrol unit with that id does not exist");
 }

 @GetMapping("/by-capability")
 public List<PatrolUnit> getPatrolUnitsByCapability(@RequestParam String name){
     return patrolUnitService.getPatrolUnitsByCapability(name);
 }

 @PutMapping("/{patrolUnitId}/capabilities/{capabilityId}")
 public ResponseEntity<String> addCapabilityToPatrolUnit(
         @PathVariable UUID patrolUnitId,
         @PathVariable UUID capabilityId){
     String r=patrolUnitService.addCapabilityToPatrolUnit(patrolUnitId,capabilityId);
     if(r.equals("PATROL_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     if(r.equals("CAPABILITY_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("That capability does not exist");
     if(r.equals("ALREADY_HAS"))
         return ResponseEntity
                 .status(409)
                 .body("The patrol unit already has that capability");
     return ResponseEntity
             .ok("Capability added to the patrol unit");
 }

 @PatchMapping("/{patrolUnitId}/sector")
 public ResponseEntity<String> movePatrolUnitToSector(
         @PathVariable UUID patrolUnitId,
         @RequestParam int sectorNumber){
     String r=patrolUnitService.movePatrolUnitToSector(patrolUnitId,sectorNumber);
     if(r.equals("PATROL_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     if(r.equals("SECTOR_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The sector with that sectorNumber does not exist");
     if(r.equals("TAKEN"))
         return ResponseEntity
                 .status(409)
                 .body("That sector is taken by another patrol unit");
     return ResponseEntity
             .ok("Sector changed successfully");
 }
}
