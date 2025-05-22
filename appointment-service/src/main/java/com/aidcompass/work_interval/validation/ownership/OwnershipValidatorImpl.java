package com.aidcompass.work_interval.validation.ownership;

import com.aidcompass.exceptions.work_interval.WorkIntervalOwnershipException;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.services.WorkIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class OwnershipValidatorImpl implements OwnershipValidator {

    private final WorkIntervalService service;


    @Override
    public void assertOwnership(UUID ownerId, Long id) {
        WorkIntervalResponseDto dto = service.findById(id);
        if (dto.ownerId() != ownerId) {
            throw new WorkIntervalOwnershipException();
        }
    }

    @Override
    public void assertOwnership(UUID ownerId, Set<WorkIntervalUpdateDto> dtoSet) {
        List<Long> ids = dtoSet.stream().map(WorkIntervalUpdateDto::id).toList();

        if (ids.size() != service.countOwnedByOwnerId(ownerId, ids)) {
            throw new WorkIntervalOwnershipException();
        }
    }
}
