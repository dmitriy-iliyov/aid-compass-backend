package com.aidcompass.work_day.validation.uniqueness;

import com.aidcompass.exceptions.work_interval.WorkIntervalAlreadyExistsException;
import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_interval.services.WorkIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UniquenessValidatorImpl implements UniquenessValidator {

    private final WorkIntervalService service;


    @Override
    public void checkUniqueness(UUID ownerId, WorkDayCreateDto dto) {
        if (!service.existsByOwnerIdAndDate(ownerId, dto.date())) {
            throw new WorkIntervalAlreadyExistsException();
        }
    }

    @Override
    public void checkUniqueness(UUID ownerId, WorkDayUpdateDto dto) {
        if (!service.existsByOwnerIdAndDate(ownerId, dto.date())) {
            throw new WorkIntervalAlreadyExistsException();
        }
    }
}
