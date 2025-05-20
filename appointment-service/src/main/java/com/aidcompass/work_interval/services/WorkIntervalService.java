package com.aidcompass.work_interval.services;

import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WorkIntervalService {
    List<WorkIntervalResponseDto> saveAll(UUID ownerId, Set<WorkIntervalCreateDto> dtoSet);

    WorkIntervalResponseDto save(UUID ownerId, WorkIntervalCreateDto dto);

    long countOwnedByOwnerId(UUID ownerId, List<Long> ids);

    List<WorkIntervalResponseDto> findAllByDate(LocalDate date);

    List<WorkIntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end);

    WorkIntervalResponseDto findById(Long id);

    List<Long> findAllEntityIdsByOwnerId(UUID ownerId);
}
