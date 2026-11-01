package rw.auca.stolenvehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.stolenvehicle.entity.Sector;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectorRepository extends JpaRepository<Sector, UUID> {
    @Query
            ("SELECT s FROM Sector s WHERE NOT EXISTS (SELECT p FROM PatrolUnit p WHERE p.sector = s) " +
                    "ORDER BY s.sectorNumber ASC")
    List<Sector> findFreeSectors();
    Optional<Sector> findBySectorNumber(int sectorNumber);
    boolean existsBySectorNumber(int sectorNumber);
}
