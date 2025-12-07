package com.aidcompass.users.jurist.controllers;


import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.users.detail.DetailService;
import com.aidcompass.users.detail.models.DetailDto;
import com.aidcompass.users.detail.models.PrivateDetailResponseDto;
import com.aidcompass.users.general.PersistFacade;
import com.aidcompass.users.jurist.models.dto.JuristDto;
import com.aidcompass.users.jurist.models.dto.JuristNameFilter;
import com.aidcompass.users.jurist.models.dto.JuristSpecializationFilter;
import com.aidcompass.users.jurist.models.dto.PrivateJuristResponseDto;
import com.aidcompass.users.jurist.services.JuristService;
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
@RequestMapping("/api/v1/jurists")
@RequiredArgsConstructor
public class JuristController {

    private final PersistFacade<JuristDto, PrivateJuristResponseDto> facade;
    private final JuristService juristService;
    private final DetailService detailService;


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid JuristDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody, HttpServletResponse response) {
        PrivateJuristResponseDto responseDto = facade.save(principal.getUserId(), dto, response);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseDto);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @GetMapping("/me")
    public ResponseEntity<?> getPrivateById(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findPrivateById(principal.getUserId()));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @GetMapping("/me/full")
    public ResponseEntity<?> getFullPrivateById(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findFullPrivateById(principal.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findPublicById(id));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<?> getFullPublicById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findFullPublicById(id));
    }


    @GetMapping
    public ResponseEntity<?> getAllByTypeAndNamesCombination(@ModelAttribute @Valid JuristNameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findAllByTypeAndNamesCombination(filter));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @PatchMapping("/me/detail")
    public ResponseEntity<?> updateDetailByJuristId(@AuthenticationPrincipal PrincipalDetails principal,
                                                    @RequestParam(value = "return_body", defaultValue = "false")
                                                    boolean returnBody,
                                                    @RequestBody @Valid DetailDto dto) {
        PrivateDetailResponseDto response = detailService.update(principal.getUserId(), dto, ServiceType.JURIST_SERVICE);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @PatchMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid JuristDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody) {
        PrivateJuristResponseDto response = juristService.update(principal.getUserId(), dto);
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
                .body(juristService.findAllApproved(page));
    }

    @GetMapping("/approved/filter")
    public ResponseEntity<?> getAllByTypeAndSpecialization(@ModelAttribute @Valid JuristSpecializationFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.findAllByTypeAndSpecialization(filter));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal PrincipalDetails principal) {
        juristService.deleteById(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristService.countByIsApproved(true));
    }
}