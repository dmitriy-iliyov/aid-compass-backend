package com.aidcompass.appointment.contracts;

import com.aidcompass.appointment.dto.AppointmentCreateDto;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.appointment.dto.AppointmentUpdateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentOrchestrator {
    AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto);

    AppointmentResponseDto update(UUID customerId, AppointmentUpdateDto updateDto);

    void complete(UUID participantId, Long id, String review);

    void cancel(UUID participantId, Long id);

    AppointmentResponseDto findById(UUID volunteerId, Long id);

    List<LocalDate> findMonthDatesByOwnerIdAndCurrentDate(UUID ownerId);
}
