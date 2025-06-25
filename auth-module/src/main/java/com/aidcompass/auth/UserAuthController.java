package com.aidcompass;

import com.aidcompass.base_dto.security.AuthRequest;
import com.aidcompass.base_dto.security.SystemAuthRequest;
import com.aidcompass.exceptions.not_found.UserNotFoundByIdException;
import com.aidcompass.security.core.models.token.models.TokenUserDetails;
import com.aidcompass.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;
    private final SystemAuthService systemAuthService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest requestDto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        userAuthService.login(requestDto, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/system-login")
    public ResponseEntity<?> systemLogin(@RequestBody @Valid SystemAuthRequest requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(systemAuthService.login(requestDto));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_CUSTOMER', 'ROLE_DOCTOR', 'ROLE_JURIST', 'ROLE_ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal TokenUserDetails principal) {
        if (userService.existsById(principal.getUserId())) {
            return ResponseEntity.ok()
                    .body(Map.of("authority", principal.getAuthorities().stream().findFirst().get().getAuthority()));
        } else {
            throw new UserNotFoundByIdException();
        }
    }
}
