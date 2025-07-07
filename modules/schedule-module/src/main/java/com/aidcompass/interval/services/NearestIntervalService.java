package com.aidcompass.interval.services;

import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface NearestIntervalService {
    Map<UUID, NearestIntervalDto> findAll(Set<UUID> ownerIds);

    NearestIntervalDto findByOwnerId(UUID ownerId);

    void deleteByOwnerId(UUID ownerId, Long id);

    void replaceIfEarlier(UUID ownerId, IntervalResponseDto dto);
}
