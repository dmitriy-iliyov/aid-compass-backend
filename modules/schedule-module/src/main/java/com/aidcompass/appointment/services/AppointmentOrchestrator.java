package com.aidcompass.appointment.services;


import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;

import java.util.UUID;

public interface AppointmentOrchestrator {
    AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto);

    AppointmentResponseDto update(UUID customerId, AppointmentUpdateDto updateDto);

    AppointmentResponseDto complete(UUID participantId, Long id, String review);

    AppointmentResponseDto cancel(UUID participantId, Long id);

    AppointmentResponseDto findByVolunteerIdAndId(UUID volunteerId, Long id);
}
