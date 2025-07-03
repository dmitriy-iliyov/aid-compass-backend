package com.aidcompass.interval.validation.ownership;

import com.aidcompass.exceptions.interval.IntervalOwnershipException;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class IntervalOwnershipValidatorImpl implements IntervalOwnershipValidator {

    private final IntervalService service;


    @Override
    public void validate(UUID ownerId, Long id) {
        IntervalResponseDto dto = service.findById(id);
        if (!dto.ownerId().equals(ownerId)) {
            throw new IntervalOwnershipException();
        }
    }
}
