package com.example.appointment;

import com.example.appointment.models.AppointmentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-appointments")
public class UserAppointmentController {

    private final UserAppointmentService userAppointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllUserAppointments(@PathVariable UUID id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Info", "Getting all user appointments");
        List<AppointmentResponseDto> userAppointments;
        try{
            userAppointments = userAppointmentService.getAllUserAppointments(id);
        } catch (Exception e){
            log.error("Error occurred while getting user appointments: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(httpHeaders)
                    .build();
        }
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(userAppointments);
    }
}
