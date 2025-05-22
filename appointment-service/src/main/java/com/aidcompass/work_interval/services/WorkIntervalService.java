package com.aidcompass.work_interval.services;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.models.dto.SystemWorkIntervalDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.models.overlaps.GlueType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WorkIntervalService {
    WorkIntervalResponseDto glue(WorkIntervalMarker dto, Long id, GlueType glueType);

    List<WorkIntervalResponseDto> saveAll(UUID ownerId, Set<WorkIntervalCreateDto> dtoSet);

    WorkIntervalResponseDto save(UUID ownerId, WorkIntervalCreateDto dto);

    long countOwnedByOwnerId(UUID ownerId, List<Long> ids);

    List<WorkIntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<SystemWorkIntervalDto> systemFindAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<WorkIntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end);

    WorkIntervalResponseDto findById(Long id);

    List<Long> findAllEntityIdsByOwnerId(UUID ownerId);

    WorkIntervalResponseDto update(WorkIntervalUpdateDto dto);

    List<WorkIntervalResponseDto> updateAll(Set<WorkIntervalUpdateDto> dtoList);

    void deleteById(Long id);

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    boolean existsByOwnerIdAndDate(UUID ownerId, LocalDate date);
}
