package rw.auca.stolenvehicle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "capability")
public class Capability {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "capabilities")
    @JsonIgnore
    private List<PatrolUnit> patrolUnits = new ArrayList<>();

    public Capability() {

    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<PatrolUnit> getPatrolUnits() {
        return patrolUnits;
    }
    public void setPatrolUnits(List<PatrolUnit> patrolUnits) {
        this.patrolUnits = patrolUnits;
    }
}
