package rw.auca.stolenvehicle.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.Sector;
import rw.auca.stolenvehicle.service.SectorService;
import java.util.*;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

 private final SectorService sectorService;

 public SectorController(SectorService sectorService){
     this.sectorService=sectorService;
 }

 @PostMapping("/save")
 public ResponseEntity<String> createSector(@RequestBody Sector x){
     sectorService.createSector(x);
     return ResponseEntity
             .status(201)
             .body("Sector saved successfully");
 }

 @GetMapping("/all")
 public List<Sector> getAllSectors(){
     return sectorService.getAllSectors();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> getSectorById(@PathVariable UUID id){
     Optional<Sector>x=sectorService.getSectorById(id);
     if(x.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("Sector with that id does not exist");
     return ResponseEntity
             .ok(x.get());
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateSector(
         @PathVariable UUID id,
         @RequestBody Sector x){
     Optional<Sector>r=sectorService.updateSector(id,x);
     if(r.isEmpty())
         return ResponseEntity
                 .status(404)
                 .body("Sector with that id does not exist");
     return ResponseEntity
             .ok(r.get());
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deleteSector(@PathVariable UUID id){
     String r=sectorService.deleteSector(id);
     if(r.equals("NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("Sector with that id does not exist");
     if(r.equals("CONFLICT"))
         return ResponseEntity
                 .status(409)
                 .body("Sector is assigned to a patrol unit");
     return ResponseEntity
             .ok("Sector deleted successfully");
 }

 @GetMapping("/free")
 public List<Sector> getFreeSectors(){
     return sectorService.getFreeSectors();
 }
}
