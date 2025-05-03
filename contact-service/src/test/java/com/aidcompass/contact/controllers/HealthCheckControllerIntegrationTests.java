package com.aidcompass.contact.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HealthCheckControllerIntegrationTests {

    @Autowired
    HealthCheckController healthCheckController;

    @Test
    @DisplayName("Health check test")
    void healthCheck_shouldReturn200() {
        ResponseEntity<?> response = healthCheckController.healthCheck();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Map.of("status", "up"), response.getBody());
    }
}
