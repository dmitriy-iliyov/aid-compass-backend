package com.aidcompass.confirmation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;


@Tag(
        name = "User's resource confirmation",
        description = "Confirmation main user resource"
)
@Log4j2
@Controller
@RequestMapping("/api/confirm")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService confirmationService;


    @Deprecated(forRemoval = true)
    @GetMapping("/email/**")
    private String getConfirmingPage(){
        return "email_confirmation";
    }

    @Operation(summary = "Confirm user email by token")
    @PostMapping("/email")
    public ResponseEntity<?> confirmEmail(@Parameter(description = "token")
                                               @RequestParam("token") String token) {
        confirmationService.validateConfirmationToken(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/users/login"));
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .headers(httpHeaders)
                .build();
    }

    @Operation(summary = "Get confirmation token")
    @PostMapping("/request")
    public ResponseEntity<?> getToken(@Parameter(description = "resource")
                                           @RequestParam("resource") String resource) throws MessagingException {
        confirmationService.sendConfirmationMessage(resource);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
