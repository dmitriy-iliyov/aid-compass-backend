package com.aidcompass.contact.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status", "up"));
    }
}
