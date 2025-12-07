package com.aidcompass.users.doctor.controllers;


import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.users.detail.DetailService;
import com.aidcompass.users.detail.models.DetailDto;
import com.aidcompass.users.detail.models.PrivateDetailResponseDto;
import com.aidcompass.users.doctor.models.dto.DoctorDto;
import com.aidcompass.users.doctor.models.dto.DoctorSpecializationFilter;
import com.aidcompass.users.doctor.models.dto.PrivateDoctorResponseDto;
import com.aidcompass.users.doctor.services.DoctorService;
import com.aidcompass.users.general.PersistFacade;
import com.aidcompass.users.general.dto.NameFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final PersistFacade<DoctorDto, PrivateDoctorResponseDto> facade;
    private final DoctorService doctorService;
    private final DetailService detailService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid DoctorDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody, HttpServletResponse response) {
        PrivateDoctorResponseDto responseDto = facade.save(principal.getUserId(), dto, response);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseDto);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @GetMapping("/me")
    public ResponseEntity<?> getPrivateById(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findPrivateById(principal.getUserId()));
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @GetMapping("/me/full")
    public ResponseEntity<?> getFullPrivateById(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findFullPrivateById(principal.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findPublicById(id));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<?> getFullPublicById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findFullPublicById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllByNamesCombination(@ModelAttribute @Valid NameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllByNamesCombination(filter));
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @PatchMapping("/me/detail")
    public ResponseEntity<?> updateDetailByDoctorId(@AuthenticationPrincipal PrincipalDetails principal,
                                                    @RequestParam(value = "return_body", defaultValue = "false")
                                                    boolean returnBody,
                                                    @RequestBody @Valid DetailDto dto) {
        PrivateDetailResponseDto response = detailService.update(principal.getUserId(), dto, ServiceType.DOCTOR_SERVICE);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @PatchMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid DoctorDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody) {
        PrivateDoctorResponseDto response = doctorService.update(principal.getUserId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/approved")
    public ResponseEntity<?> getAllApproved(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllApproved(page));
    }

    @GetMapping("/approved/{specialization}")
    public ResponseEntity<?> getAllBySpecialization(@ModelAttribute @Valid DoctorSpecializationFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllBySpecialization(filter));
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal PrincipalDetails principal) {
        doctorService.deleteById(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.countByIsApproved(true));
    }
}