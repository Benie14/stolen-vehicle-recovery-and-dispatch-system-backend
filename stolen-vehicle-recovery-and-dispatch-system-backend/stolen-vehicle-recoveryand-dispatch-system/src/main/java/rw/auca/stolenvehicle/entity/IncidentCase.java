package rw.auca.stolenvehicle.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "incident_case")
public class IncidentCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    private String lastKnownLocation;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "patrol_unit_id")
    private PatrolUnit patrolUnit;

    public IncidentCase() {

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getReportDate() {
        return reportDate;
    }
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
    public CaseStatus getStatus() {
        return status;
    }
    public void setStatus(CaseStatus status) {
        this.status = status;
    }
    public String getLastKnownLocation() {
        return lastKnownLocation;
    }
    public void setLastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public PatrolUnit getPatrolUnit() {
        return patrolUnit;
    }
    public void setPatrolUnit(PatrolUnit patrolUnit) {
        this.patrolUnit = patrolUnit;
    }
}
