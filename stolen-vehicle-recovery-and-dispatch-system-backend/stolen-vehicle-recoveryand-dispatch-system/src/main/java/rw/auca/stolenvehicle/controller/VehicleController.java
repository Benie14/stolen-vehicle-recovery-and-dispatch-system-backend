package rw.auca.stolenvehicle.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.Vehicle;
import rw.auca.stolenvehicle.service.VehicleService;
import java.util.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

 private final VehicleService vehicleService;

 public VehicleController(VehicleService vehicleService){
     this.vehicleService=vehicleService;
 }

 @PostMapping("/save")
 public ResponseEntity<String> createVehicle(@RequestBody Vehicle x){
     vehicleService.createVehicle(x);
     return ResponseEntity
             .status(201)
             .body("Vehicle saved successfully");
 }

 @GetMapping("/all")
 public List<Vehicle> getAllVehicles(){
     return vehicleService.getAllVehicles();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> getVehicleById(@PathVariable UUID id){
     Optional<Vehicle>x=vehicleService.getVehicleById(id);
     if(x.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("Vehicle with that id does not exist");
     return ResponseEntity
             .ok(x.get());
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateVehicle(
         @PathVariable UUID id,
         @RequestBody Vehicle x){
     Optional<Vehicle>r=vehicleService.updateVehicle(id,x);
     if(r.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("Vehicle with that id does not exist");
     return ResponseEntity.ok(r.get());
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deleteVehicle(@PathVariable UUID id){
     String r=vehicleService.deleteVehicle(id);
     if(r.equals("NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("Vehicle with that id does not exist");
     if(r.equals("HAS_CASES"))
         return ResponseEntity
                 .status(409)
                 .body("Vehicle has incident cases and cannot be deleted");
     return ResponseEntity
             .ok("Vehicle deleted successfully");
 }

 @GetMapping("/by-make")
 public List<Vehicle> getVehiclesByMake(@RequestParam String make){
     return vehicleService.getVehiclesByMake(make);
 }

 @GetMapping("/of-patrol-unit/{patrolUnitId}")
 public ResponseEntity<?> getVehiclesByPatrolUnit(@PathVariable UUID patrolUnitId){
     if(!vehicleService.patrolUnitExists(patrolUnitId))
         return ResponseEntity
                 .status(404)
                 .body("The patrol unit with that id does not exist");
     return ResponseEntity
             .ok(vehicleService.getVehiclesByPatrolUnit(patrolUnitId));
 }
}
