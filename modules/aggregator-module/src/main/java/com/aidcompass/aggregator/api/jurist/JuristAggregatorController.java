package com.aidcompass.aggregator.api.jurist;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.jurist.models.dto.JuristNameFilter;
import com.aidcompass.users.jurist.models.dto.JuristSpecializationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/aggregator/jurists")
@RequiredArgsConstructor
public class JuristAggregatorController {

    private final JuristAggregatorService service;


    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getPublicProfile(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPublicProfile(id));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @GetMapping("/me/profile")
    public ResponseEntity<?> getPrivateProfile(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateProfile(principal.getUserId()));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getAllApproved(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApproved(page));
    }

    @GetMapping("/cards/filter")
    public ResponseEntity<?> getJuristsByTypeAndSpecialization(@ModelAttribute @Valid JuristSpecializationFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByTypeAndSpecialization(filter));
    }

    @GetMapping("/cards/names")
    public ResponseEntity<?> getAllJuristsByNamesCombination(@ModelAttribute @Valid JuristNameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByNamesCombination(filter));
    }

    @GetMapping("/cards/gender/{gender}")
    public ResponseEntity<?> getAllByGender(@PathVariable("gender")
                                            @ValidEnum(enumClass = Gender.class, message = "Unsupported gender!")
                                            String gender,
                                            @ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByGender(Gender.toEnum(gender), page));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @DeleteMapping("/me/{password}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("password")
                                    @NotBlank(message = "Password can't be empty or blank!")
                                    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
                                    String password,
                                    HttpServletRequest request, HttpServletResponse response) {
        service.delete(principal.getUserId(), password, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}