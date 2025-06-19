package github.pedrozaz.pricealert.controller;

import github.pedrozaz.pricealert.dto.AlertRequest;
import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlertController {

    @Autowired
    AlertService alertService;

    @PostMapping("/alerts")
    public ResponseEntity<?> createAlert(@RequestBody AlertRequest request) throws InterruptedException {
        Alert alert = alertService.createAlert(request);
        return ResponseEntity.ok(alert);
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> getAlertsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(alertService.getAlertsByUserId(userId));
    }

    @DeleteMapping("/alerts/{alertId}")
    public ResponseEntity<?> deleteAlert(@PathVariable Long alertId) {
        alertService.deleteAlert(alertId);
        return ResponseEntity.ok("Alert deleted successfully.");
    }
}
