package com.aidcompass.users.doctor.controllers;

import com.aidcompass.core.general.contracts.NotificationOrchestrator;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.users.doctor.services.DoctorApprovalService;
import com.aidcompass.users.doctor.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/doctors")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminDoctorController {

    private final DoctorApprovalService approvalService;
    private final DoctorService service;
    private final NotificationOrchestrator notificationOrchestrator;


    @GetMapping("/unapproved/count")
    public ResponseEntity<?> getUnapprovedCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.countByIsApproved(false));
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapproved(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUnapproved(page));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approveDoctor(@PathVariable("id") UUID id) {
        BaseSystemVolunteerDto dto = approvalService.approve(id);
        notificationOrchestrator.greeting(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
