package com.aidcompass.appointment.services;

import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.models.dto.StatusFilter;
import com.aidcompass.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import com.aidcompass.general.contracts.dto.PageResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponseDto save(UUID customerId, LocalTime end, AppointmentCreateDto dto);

    Map<AppointmentAgeType, AppointmentResponseDto> update(UUID customerId, LocalTime end, AppointmentUpdateDto dto);

    AppointmentResponseDto findById(Long id);

    boolean existsByCustomerIdAndDateAndTimeAndStatus(UUID customerId, LocalDate date, LocalTime start, AppointmentStatus status);

    PageResponse<AppointmentResponseDto> findAllByStatusFilter(UUID participantId, StatusFilter filter,
                                                               int page, int size);

    List<AppointmentResponseDto> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date,
                                                                      AppointmentStatus status);

    List<AppointmentResponseDto> findAllByVolunteerIdAndDateInterval(UUID volunteerId, LocalDate start, LocalDate end);

    void markCompletedById(Long id, String review);

    AppointmentResponseDto markCanceledById(Long id);

    void markCanceledAllByDate(UUID participantId, LocalDate date);

    void deleteAll(UUID participantId);
}
