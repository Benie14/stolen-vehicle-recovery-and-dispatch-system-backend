package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.Capability;
import rw.auca.stolenvehicle.repository.CapabilityRepository;
import java.util.*;

@Service
public class CapabilityService {

    private final CapabilityRepository capabilityRepository;

    public CapabilityService(CapabilityRepository capabilityRepository){
        this.capabilityRepository=capabilityRepository;
    }

 public Capability createCapability(Capability capability){
        return capabilityRepository.save(capability);
    }
 public List<Capability> getAllCapabilities(){
        return capabilityRepository.findAll();
    }
 public Optional<Capability> getCapabilityById(UUID capabilityId){
        return capabilityRepository.findById(capabilityId);
    }
 public Optional<Capability> updateCapability(UUID capabilityId,Capability capability){
        if(!capabilityRepository.existsById(capabilityId))
            return Optional.empty();capability.setId(capabilityId);
            return Optional.of(capabilityRepository.save(capability));
    }
 public boolean deleteCapability(UUID capabilityId){
        if(!capabilityRepository.existsById(capabilityId))
            return false;capabilityRepository.deleteById(capabilityId);
            return true;
    }
 public List<Capability> getUnusedCapabilities(){
        return capabilityRepository.findUnusedCapabilities();
    }
}
