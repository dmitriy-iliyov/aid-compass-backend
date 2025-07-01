package com.aidcompass.appointment.contracts;

import com.aidcompass.PageResponse;
import com.aidcompass.appointment.dto.AppointmentCreateDto;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.appointment.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.dto.StatusFilter;
import com.aidcompass.appointment.enums.AppointmentStatus;
import com.aidcompass.appointment.enums.AppointmentAgeType;

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
