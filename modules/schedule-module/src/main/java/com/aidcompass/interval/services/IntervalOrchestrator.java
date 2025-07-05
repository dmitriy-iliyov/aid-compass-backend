package com.aidcompass.interval.services;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.exceptions.interval.IntervalIsInvalidException;
import com.aidcompass.exceptions.interval.IntervalNotFoundByIdException;
import com.aidcompass.exceptions.interval.IntervalTimeIsInvalidException;
import com.aidcompass.interval.models.dto.IntervalCreateDto;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.validation.ownership.IntervalOwnershipValidator;
import com.aidcompass.interval.validation.time.TimeValidator;
import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IntervalOrchestrator {

    private final IntervalService service;
    private final NearestIntervalService nearestService;
    private final AppointmentDurationService appointmentDurationService;
    private final IntervalOwnershipValidator ownershipValidator;
    private final TimeValidator timeValidator;


    @Transactional
    public IntervalResponseDto save(UUID ownerId, IntervalCreateDto inputDto) {
        Long duration = appointmentDurationService.findByOwnerId(ownerId);
        LocalTime end = inputDto.start().plusMinutes(duration);
        SystemIntervalCreatedDto dto = new SystemIntervalCreatedDto(inputDto.start(), end, inputDto.date());

        if (!timeValidator.isIntervalValid(dto)) {
            throw new IntervalIsInvalidException();
        }
        if (!timeValidator.isIntervalTimeValid(dto)) {
            throw new IntervalTimeIsInvalidException();
        }

        IntervalResponseDto existedDto = service.findByOwnerIdAndStartAndDate(ownerId, dto.start(), dto.date());
        if (existedDto != null) {
            return existedDto;
        }
        existedDto = service.save(ownerId, dto);
        nearestService.replaceIfEarlier(ownerId, existedDto);
        return existedDto;
    }

    @Transactional
    public void systemSave(UUID ownerId, SystemIntervalCreatedDto dto) {
        IntervalResponseDto existedDto = service.findByOwnerIdAndStartAndDate(ownerId, dto.start(), dto.date());
        if (existedDto != null) {
            return;
        }
        IntervalResponseDto responseDto = service.save(ownerId, dto);
        nearestService.replaceIfEarlier(ownerId, responseDto);
    }

    public void cut(AppointmentMarker dto, LocalTime end, Long id) {
        Set<IntervalResponseDto> resultDtoSet = service.cut(dto, end, id);
        List<IntervalResponseDto> resultDtoList = resultDtoSet.stream()
                .sorted(Comparator.comparing(IntervalResponseDto::date)
                        .thenComparing(IntervalResponseDto::start))
                .toList();
        nearestService.replaceIfEarlier(dto.volunteerId(), resultDtoList.get(0));
    }

    public void delete(UUID ownerId, Long id) {
        ownershipValidator.validate(ownerId, id);
        service.deleteByOwnerIdAndId(ownerId, id);
        nearestService.deleteByOwnerId(ownerId, id);
    }
}
