# Stolen Vehicle Recovery and Dispatch System Backend

**Project:** Stolen Vehicle Recovery and Dispatch System  
**Student:** NIYOKURI Berenice  
**Course:** Web Technology and Internet — AUCA

Spring Boot REST API using Spring Web, Spring Data JPA and PostgreSQL. Entity names, endpoint paths, status values, and business-rule messages follow the supplied BUILD.pdf specification.

## Requirements
- Java 17+
- Maven 3.9+
- PostgreSQL 14+

## 1. Create the database
```sql
CREATE DATABASE stolen_vehicle_db;
```

## 2. Configure database connection
Defaults in `src/main/resources/application.properties`:
- URL: `jdbc:postgresql://localhost:5432/stolen_vehicle_db`
- Username: `postgres`
- Password: `postgres`

Change the values for your local PostgreSQL installation, or set environment variables:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `PORT` (defaults to 8080)

The project uses `spring.jpa.hibernate.ddl-auto=update` and `spring.jpa.show-sql=true`.

## 3. Run
From the project root:
```bash
mvn clean spring-boot:run
```
Base URL: `http://localhost:8080`

## API endpoints
### CRUD
- `/api/capabilities`: `POST /save`, `GET /all`, `GET /{id}`, `PUT /update/{id}`, `DELETE /delete/{id}`
- `/api/sectors`: same CRUD paths
- `/api/patrol-units`: same CRUD paths
- `/api/vehicles`: same CRUD paths
- `/api/incident-cases`: same CRUD paths
- `/api/recovery-records`: `GET /all`, `POST /save?incidentCaseId={uuid}`, `DELETE /delete/{id}`

### Query requirements
- `GET /api/vehicles/by-make?make=toyota`
- `GET /api/incident-cases/by-status?status=REPORTED`
- `GET /api/incident-cases/between?start=2026-10-01&end=2026-10-31`
- `GET /api/patrol-units/by-capability?name=pursuit`
- `GET /api/sectors/free`
- `GET /api/capabilities/unused`
- `GET /api/vehicles/of-patrol-unit/{patrolUnitId}`

### Business rules
- `PATCH /api/incident-cases/{id}/status?status=DISPATCHED`
- `PUT /api/patrol-units/{patrolUnitId}/capabilities/{capabilityId}`
- `PATCH /api/patrol-units/{patrolUnitId}/sector?sectorNumber=4`
- `GET /api/recovery-records/vehicle/{plateNumber}`
- `GET /api/incident-cases/stats/by-status`
- `PATCH /api/incident-cases/cancel-day?patrolUnitId={uuid}&date=2026-10-20`
- `GET /api/dashboard/summary` (bonus)
- `GET /api/incident-cases/page?page=0&size=5&sort=reportDate,desc` (bonus)

## Postman
Import `stolen-vehicle-recoveryand-dispatch-system-midterm.postman_collection.json` into Postman. Requests are grouped/named by requirement ID. The collection uses variables `baseUrl`, `vehicleId`, `patrolUnitId`, `capabilityId`, `incidentCaseId`, and `recoveryRecordId`. Create records first and replace UUID variables with IDs from API responses before running dependent requests.

## Notes
- JPA generates UUIDs for all entity IDs.
- The seed data described in the paper must be entered through the save endpoints. No automatic data seeding is configured.
- The brief requires a public GitHub repository and a commit history showing work by part. Those actions require your own GitHub account and cannot be represented by this ZIP alone.


## Service-layer architecture
Business rules and application orchestration are placed in `service` classes:
- `PatrolUnitService`: capability assignment and sector movement rules.
- `VehicleService`: make search, patrol-unit vehicle lookup, and delete protection.
- `SectorService`: free-sector query and delete protection.
- `RecoveryRecordService`: recovered-case validation, record creation, history, and deletion.
- `CapabilityService`: capability CRUD and unused-capability query.

Repositories are intended to contain database access and the required derived/JPQL query definitions. Controllers should only handle HTTP input/output and map service results to response codes/messages.


## Method naming convention
Methods use explicit action/domain names, for example:
- `createVehicle`, `getAllVehicles`, `getVehicleById`, `updateVehicle`, `deleteVehicle`
- `createPatrolUnit`, `getAllPatrolUnits`, `getPatrolUnitById`, `addCapabilityToPatrolUnit`, `movePatrolUnitToSector`
- `createIncidentCase`, `getAllIncidentCases`, `getIncidentCaseById`, `updateIncidentCaseStatus`
- `createRecoveryRecord`, `getAllRecoveryRecords`, `getRecoveryRecordsByVehiclePlateNumber`

## Layer responsibilities
- **Controller:** HTTP route, request parameters/body, response status and message only.
- **Service:** business rules, validation, orchestration, and calls to repositories.
- **Repository:** persistence operations and required Spring Data derived/JPQL queries.
