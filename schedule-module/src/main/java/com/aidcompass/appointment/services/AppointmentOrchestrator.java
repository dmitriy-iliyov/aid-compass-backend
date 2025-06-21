package com.aidcompass.appointment.services;

import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.models.dto.AppointmentValidationInfoDto;
import com.aidcompass.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import com.aidcompass.appointment.validation.AppointmentOwnershipValidator;
import com.aidcompass.appointment.validation.AppointmentTimeValidator;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.exceptions.appointment.InvalidAttemptToCompleteException;
import com.aidcompass.exceptions.appointment.InvalidAttemptToDeleteException;
import com.aidcompass.exceptions.appointment.NotWorkingAtThisTimeException;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.overlaps.ValidationStatus;
import com.aidcompass.interval.services.IntervalOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentOrchestrator {

    private final AppointmentService service;
    private final AppointmentDurationService durationService;
    private final IntervalOrchestrator intervalOrchestrator;
    private final AppointmentTimeValidator timeValidator;
    private final AppointmentOwnershipValidator ownershipValidator;


    public AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto) {
        Long duration = durationService.findAppointmentDurationByOwnerId(dto.volunteerId());
        LocalTime end = dto.start().plusMinutes(duration);

        timeValidator.validateCustomerTime(customerId, dto);
        AppointmentValidationInfoDto info = timeValidator.validateVolunteerTime(customerId, dto, end);

        if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(dto.volunteerId(), info.intervalId());
            return service.save(customerId, end, dto);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), end, info.intervalId());
            return service.save(customerId, end, dto);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    public AppointmentResponseDto update(UUID customerId, AppointmentUpdateDto updateDto) {
        ownershipValidator.validateCustomerOwnership(customerId, updateDto.getId());

        AppointmentResponseDto currentDto = service.findById(updateDto.getId());

        UUID volunteerId = currentDto.volunteerId();
        updateDto.setVolunteerId(volunteerId);

        Long duration = durationService.findAppointmentDurationByOwnerId(volunteerId);
        LocalTime end = updateDto.start().plusMinutes(duration);

        timeValidator.validateCustomerTime(customerId, updateDto);
        AppointmentValidationInfoDto info = timeValidator.validateVolunteerTime(customerId, updateDto, end);

        Map<AppointmentAgeType, AppointmentResponseDto> responseMap;
        if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(updateDto.volunteerId(), info.intervalId());
            responseMap = service.update(customerId, end, updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.volunteerId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), end, info.intervalId());
            responseMap = service.update(customerId, end, updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.volunteerId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    public void complete(UUID participantId, Long id, String review) {
        AppointmentResponseDto dto = ownershipValidator.validateParticipantOwnership(participantId, id);
        if (dto.status().equals(AppointmentStatus.CANCELED)) {
            throw new InvalidAttemptToCompleteException();
        }
        service.markCompletedById(id, review);
    }

    public void cancel(UUID participantId, Long id) {
        AppointmentResponseDto dto = ownershipValidator.validateParticipantOwnership(participantId, id);
        // comment for test
        //timeValidator.isCompletePermit(id);
        if (dto.status().equals(AppointmentStatus.CANCELED)) {
            throw new InvalidAttemptToDeleteException();
        }
        dto = service.markCanceledById(id);
        intervalOrchestrator.systemSave(dto.volunteerId(), new SystemIntervalCreatedDto(dto.start(), dto.end(), dto.date()));
    }

    public AppointmentResponseDto findById(UUID volunteerId, Long id) {
        ownershipValidator.validateParticipantOwnership(volunteerId, id);
        return service.findById(id);
    }

    public List<LocalDate> findMonthDatesByOwnerIdAndCurrentDate(UUID ownerId) {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(27);
        return service.findAllByVolunteerIdAndDateInterval(ownerId, start, end)
                .stream()
                .map(AppointmentResponseDto::date)
                .toList();
    }
}
