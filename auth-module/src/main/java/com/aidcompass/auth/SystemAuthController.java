package com.aidcompass.auth;

import com.aidcompass.dto.security.ServiceAuthRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/v1/auth")
@RequiredArgsConstructor
public class SystemAuthController {

    private final SystemAuthService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ServiceAuthRequest requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.login(requestDto));
    }
}
