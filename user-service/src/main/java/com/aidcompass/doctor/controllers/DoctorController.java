package com.aidcompass.doctor.controllers;


import com.aidcompass.doctor.DoctorFacade;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.services.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorFacade facade;
    private final DoctorService service;


    @PostMapping("/{id}")
    public ResponseEntity<?> createDoctor(@PathVariable("id") UUID id,
                                          @RequestBody @Valid DoctorRegistrationDto dto,
                                          @RequestParam(value = "return_body", defaultValue = "false") boolean  returnBody) {
        PrivateDoctorResponseDto response = facade.save(id, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<?> setAddressByDoctorId(@PathVariable("id") UUID id,
                                                  @RequestParam("address")
                                                  @NotBlank(message = "Address can't be empty or blank!")
                                                  String address) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.setAddress(id, address));
    }

    @GetMapping("/{id}/private")
    public ResponseEntity<?> getPrivateDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(id));
    }

    @GetMapping("/{id}/public")
    public ResponseEntity<?> getPublicDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPublicById(id));
    }

    @GetMapping
    public ResponseEntity<?> getDoctorByFullName() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id") UUID id,
                                          @RequestBody @Valid DoctorUpdateDto dto,
                                          @RequestParam(value = "return_body", defaultValue = "false") boolean  returnBody) {
        PrivateDoctorResponseDto response = service.update(id, dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getUnapprovedDoctors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUnapproved());
    }

    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedDoctors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApproved());
    }

    @GetMapping("/approved/{specialization}")
    public ResponseEntity<?> getDoctorsBySpecialization(@PathVariable("specialization")
                                                        @Pattern(regexp = "^[a-zA-z]+$]")
                                                        String specialization) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApprovedBySpecialization(specialization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable("id") @Positive UUID id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}