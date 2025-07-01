package com.aidcompass.jurist;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.enums.gender.Gender;
import com.aidcompass.jurist.specialization.JuristSpecialization;
import com.aidcompass.jurist.type.JuristType;
import com.aidcompass.validation.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
    public ResponseEntity<?> getAllApproved(@RequestParam(value = "page", defaultValue = "0")
                                            @PositiveOrZero(message = "Page should be positive!")
                                            int page,
                                            @RequestParam(value = "size", defaultValue = "10")
                                            @Min(value = 10, message = "Size must be at least 10!")
                                            int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApproved(page, size));
    }

    @GetMapping("/cards/filter")
    public ResponseEntity<?> getJuristsByTypeAndSpecialization(@RequestParam(value = "type", required = false)
                                                               @ValidEnum(enumClass = JuristType.class,
                                                                          nullable = true,
                                                                          message = "Unsupported jurist type!")
                                                               String type,
                                                               @RequestParam(value = "specialization", required = false)
                                                               @ValidEnum(enumClass = JuristSpecialization.class,
                                                                          nullable = true,
                                                                          message = "Unsupported jurist specialization!")
                                                               String specialization,
                                                               @RequestParam(value = "page", defaultValue = "0")
                                                               @PositiveOrZero(message = "Page should be positive!")
                                                               int page,
                                                               @RequestParam(value = "size", defaultValue = "10")
                                                               @Min(value = 10, message = "Size must be at least 10!")
                                                               int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByTypeAndSpecialization(type, specialization, page, size));
    }

    @GetMapping("/cards/names")
    public ResponseEntity<?> getAllJuristsByNamesCombination(@RequestParam(value = "type", required = false)
                                                             @ValidEnum(enumClass = JuristType.class,
                                                                        nullable = true,
                                                                        message = "Unsupported jurist type!")
                                                             String type,

                                                             @RequestParam(value = "first_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "First name should contain only Ukrainian!")
                                                             String firstName,

                                                             @RequestParam(value = "second_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "Second name should contain only Ukrainian!")
                                                             String secondName,

                                                             @RequestParam(value = "last_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "Last name should contain only Ukrainian!")
                                                             String lastName,

                                                             @RequestParam(value = "page", defaultValue = "0")
                                                             @PositiveOrZero(message = "Page should be positive!")
                                                             int page,
                                                             @RequestParam(value = "size", defaultValue = "10")
                                                             @Min(value = 10, message = "Size must be at least 10!")
                                                             int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByNamesCombination(type, firstName, secondName, lastName, page, size));
    }

    @GetMapping("/cards/gender/{gender}")
    public ResponseEntity<?> getAllByGender(@PathVariable("gender")
                                            @ValidEnum(enumClass = Gender.class, message = "Unsupported gender!")
                                            String gender,
                                            @RequestParam(value = "page", defaultValue = "0")
                                            @PositiveOrZero(message = "Page should be positive!")
                                            int page,
                                            @RequestParam(value = "size", defaultValue = "10")
                                            @Min(value = 10, message = "Size must be at least 10!")
                                            int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByGender(Gender.toEnum(gender), page, size));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        service.delete(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}