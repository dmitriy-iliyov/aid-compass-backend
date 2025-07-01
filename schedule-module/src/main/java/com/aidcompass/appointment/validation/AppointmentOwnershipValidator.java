package com.aidcompass.appointment.validation;

import com.aidcompass.appointment.contracts.AppointmentService;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.exceptions.appointment.AppointmentOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentOwnershipValidator {

    private final AppointmentService service;


    public void validateCustomerOwnership(UUID customerId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.customerId().equals(customerId)) {
            throw new AppointmentOwnershipException();
        }
    }

    public AppointmentResponseDto validateParticipantOwnership(UUID participantId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.customerId().equals(participantId) && !dto.volunteerId().equals(participantId)) {
            throw new AppointmentOwnershipException();
        }
        return dto;
    }
}
