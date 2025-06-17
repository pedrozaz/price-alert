package github.pedrozaz.pricealert.controller;

import github.pedrozaz.pricealert.dto.AlertRequest;
import github.pedrozaz.pricealert.entity.Alert;
import github.pedrozaz.pricealert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

    @Autowired
    AlertService alertService;

    @PostMapping("/alerts")
    public ResponseEntity<?> createAlert(@RequestBody AlertRequest request) {
        Alert alert = alertService.createAlert(request);
        return ResponseEntity.ok(alert);
    }
}
