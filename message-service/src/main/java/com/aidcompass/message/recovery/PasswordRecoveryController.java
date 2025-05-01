package com.aidcompass.message.recovery;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "Password recovery",
        description = "Method to password recovery"
)
@RestController
@RequestMapping("/api/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @Operation(summary = "Send recovery link to user resource")
    @PostMapping("/request")
    public ResponseEntity<?> passwordRecoveryRequest(@Parameter(description = "user resource")
                                                         @RequestParam("resource") String recourse) throws MessagingException {
        passwordRecoveryService.sendRecoveryMessage(recourse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Reset password by token")
    @PatchMapping("/recover")
    public ResponseEntity<?> setNewPassword(@Parameter(description = "verifying token")
                                                @RequestParam("token") String token,
                                            @Parameter(description = "password")
                                                @RequestBody Map<String, String> payload) {
        passwordRecoveryService.recoverPassword(token, payload.get("password"));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
