package com.aidcompass.interval.services;

import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SystemIntervalService {
    SystemIntervalDto systemFindById(Long id);

    SystemIntervalDto systemFindNearestByOwnerId(UUID ownerId);

    List<SystemIntervalDto> systemFindAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    long countOwnedByOwnerId(UUID ownerId, List<Long> ids);

}
