package rw.auca.stolenvehicle.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "patrol_unit")
public class PatrolUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String unitName;

    private String commanderName;

    private String radioCode;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToMany
    @JoinTable(name = "patrol_unit_capability",
        joinColumns = @JoinColumn(name = "patrol_unit_id"),
        inverseJoinColumns = @JoinColumn(name = "capability_id"))

    private List<Capability> capabilities = new ArrayList<>();

    public PatrolUnit() {

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getCommanderName() {
        return commanderName;
    }
    public void setCommanderName(String commanderName) {
        this.commanderName = commanderName;
    }
    public String getRadioCode() {
        return radioCode;
    }
    public void setRadioCode(String radioCode) {
        this.radioCode = radioCode;
    }
    public Sector getSector() {
        return sector;
    }
    public void setSector(Sector sector) {
        this.sector = sector;
    }
    public List<Capability> getCapabilities() {
        return capabilities;
    }
    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }
}
