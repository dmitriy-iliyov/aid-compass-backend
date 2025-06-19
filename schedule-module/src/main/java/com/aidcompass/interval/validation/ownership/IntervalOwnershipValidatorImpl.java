package com.aidcompass.interval.validation.ownership;

import com.aidcompass.exceptions.interval.IntervalOwnershipException;
import com.aidcompass.interval.models.dto.SystemIntervalDto;
import com.aidcompass.interval.services.SystemIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class IntervalOwnershipValidatorImpl implements IntervalOwnershipValidator {

    private final SystemIntervalService service;


    @Override
    public void validate(UUID ownerId, Long id) {
        SystemIntervalDto dto = service.systemFindById(id);
        if (!dto.ownerId().equals(ownerId)) {
            throw new IntervalOwnershipException();
        }
    }
}
