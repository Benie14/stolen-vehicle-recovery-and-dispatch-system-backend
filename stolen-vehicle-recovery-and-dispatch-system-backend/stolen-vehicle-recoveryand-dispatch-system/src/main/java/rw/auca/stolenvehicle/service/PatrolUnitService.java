package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.*;
import rw.auca.stolenvehicle.repository.*;
import java.util.*;

@Service
public class PatrolUnitService {

    private final PatrolUnitRepository patrolUnitRepository;
    private final CapabilityRepository capabilityRepository;
    private final SectorRepository sectorRepository;

    public PatrolUnitService(
            PatrolUnitRepository patrolUnitRepository,
            CapabilityRepository capabilityRepository,
            SectorRepository sectorRepository){
        this.patrolUnitRepository=patrolUnitRepository;
        this.capabilityRepository=capabilityRepository;
        this.sectorRepository=sectorRepository;
    }
    public PatrolUnit createPatrolUnit(PatrolUnit x){
        return patrolUnitRepository.save(x);
    }
    public List<PatrolUnit> getAllPatrolUnits(){
        return patrolUnitRepository.findAll();
    }
    public Optional<PatrolUnit> getPatrolUnitById(UUID id){
        return patrolUnitRepository.findById(id);
    }
    public Optional<PatrolUnit> updatePatrolUnit(UUID id,PatrolUnit x){
        if(!patrolUnitRepository
                .existsById(id))
            return Optional.empty();
        x.setId(id);
        return Optional
                .of(patrolUnitRepository.save(x));
    }
    public boolean deletePatrolUnit(UUID id){
        if(!patrolUnitRepository.existsById(id))
            return false;
        patrolUnitRepository.deleteById(id);
        return true;
    }
    public List<PatrolUnit> getPatrolUnitsByCapability(String name){
        return patrolUnitRepository.findByCapabilityNameIgnoreCase(name);
    }
    public String addCapabilityToPatrolUnit(
            UUID patrolId,
            UUID capabilityId){Optional<PatrolUnit> p=patrolUnitRepository
            .findById(patrolId);
        if(p.isEmpty())
            return "PATROL_NOT_FOUND";
        Optional<Capability> c=capabilityRepository
                .findById(capabilityId);
        if(c.isEmpty())
            return "CAPABILITY_NOT_FOUND";
        if(p.get()
                .getCapabilities()
                .stream()
                .anyMatch(v->v.getId()
                        .equals(capabilityId)))
            return "ALREADY_HAS";
        p.get().getCapabilities().add(c.get());
        patrolUnitRepository.save(p.get());
        return "ADDED";
    }
 public String movePatrolUnitToSector(
         UUID patrolId,
         int sectorNumber){
        Optional<PatrolUnit> p=patrolUnitRepository.findById(patrolId);
        if(p.isEmpty())
            return "PATROL_NOT_FOUND";Optional<Sector> s=sectorRepository.findBySectorNumber(sectorNumber);
            if(s.isEmpty())
                return "SECTOR_NOT_FOUND";
            if(patrolUnitRepository.existsBySector_Id(s.get().getId())&&(p.get().getSector()==null||
                    !p.get().getSector().getId().equals(s.get().getId())))
                return "TAKEN";p.get().setSector(s.get());
                patrolUnitRepository.save(p.get());
                return "MOVED";
    }
}
