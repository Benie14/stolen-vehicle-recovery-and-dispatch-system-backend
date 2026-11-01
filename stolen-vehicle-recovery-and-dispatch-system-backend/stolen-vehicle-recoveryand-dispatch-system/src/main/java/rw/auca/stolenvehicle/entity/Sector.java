package rw.auca.stolenvehicle.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sector")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int sectorNumber;

    private String name;

    public Sector() {

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public int getSectorNumber() {
        return sectorNumber;
    }
    public void setSectorNumber(int sectorNumber) {
        this.sectorNumber = sectorNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
