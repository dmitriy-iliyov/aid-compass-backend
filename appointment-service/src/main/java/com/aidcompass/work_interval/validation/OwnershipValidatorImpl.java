package com.aidcompass.work_interval.validation;

import com.aidcompass.exceptions.OwnershipException;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.services.WorkIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class OwnershipValidatorImpl implements OwnershipValidator {

    private final WorkIntervalService service;


    @Override
    public void assertOwnership(UUID ownerId, Long id) {
        WorkIntervalResponseDto dto = service.findById(id);
        if (dto.ownerId() != ownerId) {
            throw new OwnershipException();
        }
    }

    @Override
    public void assertOwnership(UUID ownerId, List<Long> ids) {
        if (ids.size() != service.countOwnedByOwnerId(ownerId, ids)) {
            throw new OwnershipException();
        }
    }
}
