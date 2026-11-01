package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;
import rw.auca.stolenvehicle.entity.Sector;
import rw.auca.stolenvehicle.repository.*;
import java.util.*;

@Service
public class SectorService {
    private final SectorRepository sectorRepository;
    private final PatrolUnitRepository patrolUnitRepository;

    public SectorService(
            SectorRepository sectorRepository,
            PatrolUnitRepository patrolUnitRepository){
        this.sectorRepository=sectorRepository;
        this.patrolUnitRepository=patrolUnitRepository;
    }

    public Sector createSector(Sector x){
        return sectorRepository.save(x);
    }
    public List<Sector> getAllSectors(){
        return sectorRepository.findAll();
    }
    public Optional<Sector> getSectorById(UUID id){
        return sectorRepository.findById(id);
    }
    public Optional<Sector> updateSector(UUID id,Sector x){
        if(!sectorRepository.existsById(id))
            return Optional.empty();
        x.setId(id);
        return Optional.of(sectorRepository.save(x));
    }
    public String deleteSector(UUID id){
        if(!sectorRepository.existsById(id))
            return "NOT_FOUND";
        if(patrolUnitRepository.existsBySector_Id(id))
            return "CONFLICT";
        sectorRepository.deleteById(id);
        return "DELETED";
    }
    public List<Sector> getFreeSectors(){
        return sectorRepository.findFreeSectors();
    }
}
