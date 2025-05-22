package com.aidcompass.appointment_duration;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment/duration/{owner_id}")
@RequiredArgsConstructor
public class AppointmentDurationController {

    private final AppointmentDurationService service;


    @PostMapping
    public ResponseEntity<?> setAppointmentDuration(@PathVariable("owner_id") UUID ownerId,
                                                    @RequestParam("duration")
                                                    @NotNull(message = "Duration shouldn't be null!")
                                                    @Min(value = 10, message = "Duration should be at least 15 minutes.")
                                                    @Max(value = 60, message = "Duration should be at most 60 minutes.")
                                                    Long duration,
                                                    @RequestParam(value = "return_body", defaultValue = "false")
                                                    boolean  returnBody) {
        Long response = service.setAppointmentDuration(ownerId, duration);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAppointmentDurationByOwnerId(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAppointmentDurationByOwnerId(ownerId));
    }
}
