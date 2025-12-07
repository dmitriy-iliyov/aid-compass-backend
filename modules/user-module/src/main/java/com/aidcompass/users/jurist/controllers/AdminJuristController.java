package com.aidcompass.users.jurist.controllers;

import com.aidcompass.core.general.contracts.NotificationOrchestrator;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.users.jurist.services.JuristService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/jurists")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminJuristController {

    private final JuristService service;
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
    public ResponseEntity<?> approve(@PathVariable("id") UUID id) {
        BaseSystemVolunteerDto dto = service.approve(id);
        notificationOrchestrator.greeting(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
