package rw.auca.stolenvehicle.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.entity.Capability;
import rw.auca.stolenvehicle.service.CapabilityService;
import java.util.*;

@RestController
@RequestMapping("/api/capabilities")
public class CapabilityController {

    private final CapabilityService capabilityService;

    public CapabilityController(CapabilityService capabilityService){
        this.capabilityService=capabilityService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createCapability(@RequestBody Capability x){
        capabilityService.createCapability(x);
        return ResponseEntity
                .status(201)
                .body("Capability saved successfully");
    }

    @GetMapping("/all")
    public List<Capability> getAllCapabilities(){
        return capabilityService.getAllCapabilities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCapabilityById(@PathVariable UUID id){
        Optional<Capability>x=capabilityService.getCapabilityById(id);
        if(x.isEmpty())
            return ResponseEntity
                    .status(404)
                    .body("Capability with that id does not exist");
        return ResponseEntity.ok(x.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCapability(
            @PathVariable UUID id,
            @RequestBody Capability x){
        Optional<Capability>r=capabilityService.updateCapability(id,x);
        if(r.isEmpty())
            return ResponseEntity
                    .status(404)
                    .body("Capability with that id does not exist");
        return ResponseEntity.ok(r.get());
    }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> deleteCapability(@PathVariable UUID id){
        return capabilityService.deleteCapability(id)?ResponseEntity.ok("Capability deleted successfully"):ResponseEntity
                .status(404)
                .body("Capability with that id does not exist");
    }

 @GetMapping("/unused")
 public List<Capability> getUnusedCapabilities(){
        return capabilityService.getUnusedCapabilities();
    }
}
