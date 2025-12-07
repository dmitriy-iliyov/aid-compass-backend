package com.aidcompass.aggregator.api.admin;


import com.aidcompass.aggregator.api.appointment.AppointmentAggregatorService;
import com.aidcompass.aggregator.api.customer.CustomerAggregatorService;
import com.aidcompass.aggregator.api.doctor.DoctorAggregatorService;
import com.aidcompass.aggregator.api.jurist.JuristAggregatorService;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import com.aidcompass.users.general.dto.NameFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/aggregator")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminAggregatorController {

    private final DoctorAggregatorService doctorAggregatorService;
    private final JuristAggregatorService juristAggregatorService;
    private final AppointmentAggregatorService appointmentAggregatorService;
    private final CustomerAggregatorService customerAggregatorService;


    @GetMapping("/doctors/unapproved/cards")
    public ResponseEntity<?> getUnapprovedDoctors(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorAggregatorService.findAllUnapproved(page));
    }

    @GetMapping("/jurists/unapproved/cards")
    public ResponseEntity<?> getUnapprovedJurists(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristAggregatorService.findAllUnapproved(page));
    }

    @GetMapping("/doctors/unapproved/cards/names")
    public ResponseEntity<?> getUnapprovedDoctorsByNames(@ModelAttribute @Valid NameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorAggregatorService.findAllUnapprovedByNamesCombination(filter));
    }

    @GetMapping("/jurists/unapproved/cards/names")
    public ResponseEntity<?> getUnapprovedJuristsByNames(@ModelAttribute @Valid NameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juristAggregatorService.findAllUnapprovedByNamesCombination(filter));
    }

    @GetMapping("/{participant_id}/appointments")
    public ResponseEntity<?> getParticipantAppointments(@PathVariable("participant_id")
                                                        UUID participantId,
                                                        @ModelAttribute @Valid StatusFilter filter,
                                                        @RequestParam("for_volunteer")
                                                        boolean forVolunteer) {
        if (forVolunteer) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(appointmentAggregatorService.findByFilterAndVolunteerId(participantId, filter));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(appointmentAggregatorService.findByFilterAndCustomerId(participantId, filter));
    }

    @GetMapping("/customers/names")
    public ResponseEntity<?> findByNameCombination(@ModelAttribute @Valid NameFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAggregatorService.findAllByNamesCombination(filter));
    }
}