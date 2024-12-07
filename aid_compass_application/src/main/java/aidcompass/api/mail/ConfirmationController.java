package aidcompass.api.mail;


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


@Log4j2
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService validationService;


    @GetMapping("/confirm")
    private String getConfirmingPage(@RequestParam("token") String token){
        return "email_confirmation";
    }

    @PostMapping("/confirm")
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Confirming email");

        try{
            if (validationService.validateConfirmationAnswer(token)){
                httpHeaders.setLocation(URI.create("/users/login"));
                return ResponseEntity
                        .status(HttpStatus.SEE_OTHER)
                        .headers(httpHeaders)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(httpHeaders)
                    .build();
        } catch (Exception e){
            log.error("Error while validation email: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
    }
}
