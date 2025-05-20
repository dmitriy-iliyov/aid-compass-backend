package com.aidcompass.doctor.controllers;


import com.aidcompass.detail.DetailService;
import com.aidcompass.detail.models.ServiceType;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.doctor.PersistFacade;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.page.PageDto;
import com.aidcompass.doctor.services.DoctorService;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    private final PersistFacade facade;
    private final DoctorService doctorService;
    private final DetailService detailService;


    @PostMapping("/{id}")
    public ResponseEntity<?> createDoctor(@PathVariable("id") UUID id,
                                          @RequestBody @Valid DoctorRegistrationDto dto,
                                          @RequestParam(value = "return_body", defaultValue = "false")
                                          boolean  returnBody) {
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

    @GetMapping("/{id}/private")
    public ResponseEntity<?> getPrivateDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findPrivateById(id));
    }

    @GetMapping("/{id}/private/full")
    public ResponseEntity<?> getFullPrivateDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findFullPrivateById(id));
    }

    @GetMapping("/{id}/public")
    public ResponseEntity<?> getPublicDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findPublicById(id));
    }

    @GetMapping("/{id}/public/full")
    public ResponseEntity<?> getFullPublicDoctorById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findFullPublicById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllDoctorsByNamesCombination(@RequestParam(value = "first_name", required = false)
                                                  @NotBlank(message = "First name shouldn't be empty or blank!")
                                                  @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
                                                  @Pattern(
                                                          regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                          message = "First name should contain only Ukrainian!"
                                                  )
                                                  String firstName,

                                                     @RequestParam(value = "second_name", required = false)
                                                  @NotBlank(message = "Second name shouldn't be empty or blank!")
                                                  @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
                                                  @Pattern(
                                                          regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                          message = "Second name should contain only Ukrainian!"
                                                  )
                                                  String secondName,

                                                     @RequestParam(value = "last_name", required = false)
                                                  @NotBlank(message = "Last name shouldn't be empty or blank!")
                                                  @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
                                                  @Pattern(
                                                          regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                          message = "Last name should contain only Ukrainian!"
                                                  )
                                                  String lastName,
                                                  @RequestBody @Valid PageDto page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllByNamesCombination(firstName, secondName, lastName, page));
    }

    @PatchMapping("/{id}/detail")
    public ResponseEntity<?> updateDetailByDoctorId(@PathVariable("id") UUID id,
                                                    @RequestParam(value = "return_body", defaultValue = "false")
                                                    boolean returnBody,
                                                    @RequestBody @Valid DetailDto dto) {
        PrivateDetailResponseDto response = detailService.updateWithCache(id, dto, ServiceType.DOCTOR_SERVICE);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id") UUID id,
                                          @RequestBody @Valid DoctorUpdateDto dto,
                                          @RequestParam(value = "return_body", defaultValue = "false")
                                          boolean  returnBody) {
        PrivateDoctorResponseDto response = doctorService.update(id, dto);
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
    public ResponseEntity<?> getUnapprovedDoctors(@RequestBody @Valid PageDto page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllUnapproved(page));
    }

    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedDoctors(@RequestBody @Valid PageDto page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllApproved(page));
    }

    @GetMapping("/approved/{specialization}")
    public ResponseEntity<?> getDoctorsBySpecialization(@PathVariable("specialization")
                                                        @Pattern(regexp = "^[a-zA-z]+$]")
                                                        DoctorSpecialization specialization,
                                                        @RequestBody @Valid PageDto page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorService.findAllApprovedBySpecialization(specialization, page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable("id") @Positive UUID id) {
        doctorService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}