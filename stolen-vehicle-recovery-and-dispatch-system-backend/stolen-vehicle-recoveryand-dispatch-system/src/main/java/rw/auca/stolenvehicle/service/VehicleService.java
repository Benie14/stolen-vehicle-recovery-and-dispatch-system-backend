package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.Vehicle;
import rw.auca.stolenvehicle.repository.*;
import java.util.*;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final PatrolUnitRepository patrolUnitRepository;
    private final IncidentCaseRepository incidentCaseRepository;

 public VehicleService(
         VehicleRepository vehicleRepository,
         PatrolUnitRepository patrolUnitRepository,
         IncidentCaseRepository incidentCaseRepository){
     this.vehicleRepository=vehicleRepository;
     this.patrolUnitRepository=patrolUnitRepository;
     this.incidentCaseRepository=incidentCaseRepository;
 }

 public Vehicle createVehicle(Vehicle vehicle){
     return vehicleRepository.save(vehicle);
 }
 public List<Vehicle> getAllVehicles(){
     return vehicleRepository.findAll();
 }
 public Optional<Vehicle> getVehicleById(UUID id){
     return vehicleRepository.findById(id);
 }
 public Optional<Vehicle> updateVehicle(UUID id,Vehicle vehicle){
     if(!vehicleRepository.existsById(id))
         return Optional.empty();
     vehicle.setId(id);
     return Optional.of(vehicleRepository.save(vehicle));
 }
 public String deleteVehicle(UUID id){
     if(!vehicleRepository.existsById(id))
         return "NOT_FOUND";
     if(incidentCaseRepository.existsByVehicle_Id(id))
         return "HAS_CASES";
     vehicleRepository.deleteById(id);
     return "DELETED";
 }
 public List<Vehicle> getVehiclesByMake(String make){
     return vehicleRepository.findByMakeIgnoreCaseOrderByModelAsc(make);
 }
 public boolean patrolUnitExists(UUID id){
     return patrolUnitRepository.existsById(id);
 }
 public List<Vehicle> getVehiclesByPatrolUnit(UUID id){
     return incidentCaseRepository.findVehiclesByPatrolUnitId(id);
 }
}
