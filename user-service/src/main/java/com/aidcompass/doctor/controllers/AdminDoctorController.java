package com.aidcompass.doctor.controllers;

import com.aidcompass.doctor.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/doctors")
@RequiredArgsConstructor
public class AdminDoctorController {

    private final DoctorService doctorService;


    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveDoctor(@PathVariable("id") UUID id) {
        doctorService.approve(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
