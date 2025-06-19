package com.aidcompass.user.controllers;


import com.aidcompass.confirmation.services.AccountResourceConfirmationService;
import com.aidcompass.contracts.UserOrchestrator;
import com.aidcompass.base_dto.*;
import com.aidcompass.security.core.models.token.models.TokenUserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_DOCTOR', 'ROLE_JURIST', 'ROLE_CUSTOMER')")
@RequiredArgsConstructor
public class UserController {

    private final UserOrchestrator userOrchestrator;
    private final AccountResourceConfirmationService confirmationService;


    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRegistrationDto userRegistrationDto) throws Exception {
        userOrchestrator.save(userRegistrationDto);
        confirmationService.sendConfirmationMessage(userRegistrationDto.email());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of("message", "На вашу електронну адресу надіслано лист із підтвердженням. " +
                                                  "Будь ласка, перевірте поштову скриньку, щоб завершити реєстрацію.")
                );
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> get(@AuthenticationPrincipal TokenUserDetails principal){
        return ResponseEntity.ok(userOrchestrator.findById(principal.getUserId()));
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal TokenUserDetails principal,
                                    @RequestBody UserUpdateDto userUpdateDto,
                                    @RequestParam(value = "return_body", defaultValue = "false") boolean returnBody) {
        UserResponseDto response = userOrchestrator.update(principal.getUserId(), userUpdateDto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal TokenUserDetails principal) {
        userOrchestrator.deleteById(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/me/{password}")
    public ResponseEntity<?> deleteByPassword(@AuthenticationPrincipal TokenUserDetails principal,
                                              @PathVariable("password")
                                              @NotBlank(message = "Password can't be empty or blank!")
                                              @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
                                              String password) {
        userOrchestrator.deleteByPassword(principal.getUserId(), password);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}