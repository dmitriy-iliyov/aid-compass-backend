package com.aidcompass.work_day;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_day.services.WorkDayService;
import com.aidcompass.work_day.validation.uniqueness.UniquenessValidator;
import com.aidcompass.work_interval.validation.duration.DurationValidator;
import com.aidcompass.work_interval.validation.ownership.OwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class  WorkDayFacade {

    private final WorkDayService service;
    private final OwnershipValidator ownershipValidator;
    private final DurationValidator durationValidator;
    private final UniquenessValidator uniquenessValidator;


    public WorkDayResponseDto save(UUID ownerId, WorkDayCreateDto dto) {
        uniquenessValidator.checkUniqueness(ownerId, dto);
        durationValidator.validateWorkIntervalDuration(ownerId, new HashSet<>(dto.workIntervals()));
        return service.save(ownerId, dto);
    }

    public WorkDayResponseDto update(UUID ownerId, WorkDayUpdateDto dto) {
        ownershipValidator.assertOwnership(ownerId, dto.workIntervals());
        durationValidator.validateWorkIntervalDuration(ownerId, new HashSet<>(dto.workIntervals()));
        return service.update(dto);
    }
}