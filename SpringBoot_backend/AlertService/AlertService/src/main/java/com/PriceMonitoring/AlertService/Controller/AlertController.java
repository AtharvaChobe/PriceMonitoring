package com.PriceMonitoring.AlertService.Controller;

import com.PriceMonitoring.AlertService.Entity.Alert;
import com.PriceMonitoring.AlertService.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @PostMapping
    public ResponseEntity<?> saveAlert(@RequestBody Alert alert){
        return ResponseEntity.ok(alertService.save(alert));
    }

    @GetMapping
    public ResponseEntity<?> getAllAlerts(){
        return ResponseEntity.ok(alertService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable long userId){
        return ResponseEntity.ok(alertService.getByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletAlert(@PathVariable long id){
        alertService.delete(id);
        return ResponseEntity.ok("Alert Deleted");
    }
}
