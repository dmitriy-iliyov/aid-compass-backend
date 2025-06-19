package com.aidcompass.appointment;

import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.services.AppointmentOrchestrator;
import com.aidcompass.appointment.services.AppointmentService;
import com.aidcompass.contracts.PrincipalDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentOrchestrator orchestrator;
    private final AppointmentService service;


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/me")
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principle,
                                    @RequestBody @Valid AppointmentCreateDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody){
        AppointmentResponseDto response = orchestrator.save(principle.getUserId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_JURIST', 'ROLE_DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id")
                                 @Positive(message = "Id should be positive!")
                                 Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_JURIST', 'ROLE_DOCTOR')")
    @PatchMapping("/me/{id}/complete")
    public ResponseEntity<?> complete(@AuthenticationPrincipal PrincipalDetails principle,
                                      @PathVariable("id")
                                      @Positive(message = "Id should be positive!")
                                      Long id,
                                      @RequestBody
                                      @NotBlank(message = "Review shouldn't be empty or blank!")
                                      @Size(max = 1000, message = "Review should be less then 1000 character!")
                                      String review) {
        orchestrator.complete(principle.getUserId(), id, review);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principle,
                                    @RequestBody @Valid AppointmentUpdateDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody){
        AppointmentResponseDto response = orchestrator.update(principle.getUserId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_JURIST', 'ROLE_DOCTOR')")
    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principle,
                                    @PathVariable("id")
                                    @Positive(message = "Id should be positive!")
                                    Long id) {
        orchestrator.delete(principle.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
