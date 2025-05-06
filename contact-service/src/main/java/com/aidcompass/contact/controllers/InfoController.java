package com.aidcompass.contact.controllers;

import com.aidcompass.contact_type.models.ContactType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts/info")
public class InfoController {

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status", "up"));
    }

    @GetMapping("/enums")
    public ResponseEntity<?> getAllEnums() {
        Map<String, String> enums = Map.of((ContactType.class).getName(), Arrays.toString(ContactType.values()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enums);
    }
}
