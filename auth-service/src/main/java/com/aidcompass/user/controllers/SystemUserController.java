package com.aidcompass.user.controllers;

import com.aidcompass.clients.RecoveryRequestDto;
import com.aidcompass.user.services.UserFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/system/v1/users")
@RequiredArgsConstructor
public class SystemUserController {

    private final UserFacade userFacade;

    // only for message service
    @PatchMapping("/confirm/email")
    public ResponseEntity<?> confirmEmail(@RequestParam("email")
                                          @NotBlank(message = "Email shouldn't be empty or blank!")
                                          @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
                                          @Email(message = "Email should be valid!")
//                                          @ExistEmail(message = "Email isn't exist!")
                                          String email) {
        userFacade.confirmByEmail(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // only for message service
    @PatchMapping("/recover-password/email")
    public ResponseEntity<?> recoveryPasswordByPassword(@RequestBody @Valid RecoveryRequestDto recoveryRequestDto) {
        userFacade.recoverPasswordByEmail(recoveryRequestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
