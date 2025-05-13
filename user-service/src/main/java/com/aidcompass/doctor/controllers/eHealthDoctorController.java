package com.aidcompass.doctor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors/e-health")
@RequiredArgsConstructor
public class eHealthDoctorController {


    @PostMapping("/ehealth/login")
    public ResponseEntity<?> eHealthLogin() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
