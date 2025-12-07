package com.aidcompass.aggregator.api.appointment;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aggregator/appointments")
@RequiredArgsConstructor
public class AppointmentAggregatorController {

    private final AppointmentAggregatorService service;


    @PreAuthorize("hasAnyAuthority('ROLE_JURIST', 'ROLE_DOCTOR')")
    @GetMapping("/me/{id}")
    public ResponseEntity<?> getVolunteerAppointment(@AuthenticationPrincipal PrincipalDetails principal,
                                                     @PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findFullAppointment(principal.getUserId(), id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_JURIST', 'ROLE_DOCTOR')")
    @GetMapping("/filter")
    public ResponseEntity<?> getAppointments(@AuthenticationPrincipal PrincipalDetails principal,
                                             @ModelAttribute @Valid StatusFilter filter) {
        List<Authority> authorityList = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authority::valueOf)
                .toList();
        if (authorityList.contains(Authority.ROLE_CUSTOMER)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.findByFilterAndCustomerId(principal.getUserId(), filter));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByFilterAndVolunteerId(principal.getUserId(), filter));
    }
}
