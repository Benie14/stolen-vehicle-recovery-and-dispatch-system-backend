package rw.auca.stolenvehicle.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "recovery_record")
public class RecoveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String locationFound;

    private String remarks;

    private int distanceKm;

    private LocalDate recoveredDate;

    @ManyToOne
    @JoinColumn(name = "incident_case_id")
    private IncidentCase incidentCase;

    public RecoveryRecord() {

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getLocationFound() {
        return locationFound;
    }
    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public int getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }
    public LocalDate getRecoveredDate() {
        return recoveredDate;
    }
    public void setRecoveredDate(LocalDate recoveredDate) {
        this.recoveredDate = recoveredDate;
    }
    public IncidentCase getIncidentCase() {
        return incidentCase;
    }
    public void setIncidentCase(IncidentCase incidentCase) {
        this.incidentCase = incidentCase;
    }
}
