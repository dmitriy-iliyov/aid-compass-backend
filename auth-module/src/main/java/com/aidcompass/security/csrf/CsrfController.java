package com.aidcompass.security.csrf;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/csrf")
@RequiredArgsConstructor
public class CsrfController {

    private final CsrfServiceImpl csrfTokenService;

    @GetMapping
    public ResponseEntity<?> getCsrf(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CsrfToken maskedCsrfToken = csrfTokenService.getMaskedToken(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(maskedCsrfToken);
    }
}
