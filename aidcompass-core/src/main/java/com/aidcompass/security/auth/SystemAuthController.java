package com.aidcompass.security.auth;

import com.aidcompass.security.auth.dto.ServiceAuthRequest;
import com.aidcompass.security.auth.services.SystemAuthService;
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
