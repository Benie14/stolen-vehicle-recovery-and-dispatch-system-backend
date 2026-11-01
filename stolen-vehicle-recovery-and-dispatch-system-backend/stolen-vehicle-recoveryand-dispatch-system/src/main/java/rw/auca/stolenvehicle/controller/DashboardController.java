package rw.auca.stolenvehicle.controller;

import org.springframework.web.bind.annotation.*;
import rw.auca.stolenvehicle.service.DashboardService;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

 private final DashboardService dashboardService;

 public DashboardController(DashboardService dashboardService){
     this.dashboardService=dashboardService;
 }

 @GetMapping("/summary")
 public Map<String,Long> getDashboardSummary(){
     return dashboardService.summary();
 }
}
