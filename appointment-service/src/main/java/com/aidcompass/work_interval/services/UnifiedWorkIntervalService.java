package com.aidcompass.work_interval.services;

import com.aidcompass.exceptions.WorkIntervalNotFoundByIdException;
import com.aidcompass.work_interval.WorkIntervalRepository;
import com.aidcompass.work_interval.mapper.WorkIntervalMapper;
import com.aidcompass.work_interval.models.WorkIntervalEntity;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedWorkIntervalService implements WorkIntervalService, UpdateWorkIntervalService, DeleteWorkIntervalService {

    private final WorkIntervalRepository repository;
    private final WorkIntervalMapper mapper;


    @Transactional
    @Override
    public List<WorkIntervalResponseDto> saveAll(UUID ownerId, Set<WorkIntervalCreateDto> dtoSet) {
        List<WorkIntervalEntity> entityList = repository.saveAll(mapper.toEntityList(ownerId, dtoSet));
        return mapper.toDtoList(entityList);
    }

    @Transactional
    @Override
    public WorkIntervalResponseDto save(UUID ownerId, WorkIntervalCreateDto dto) {
        WorkIntervalEntity entity = repository.save(mapper.toEntity(ownerId, dto));
        return mapper.toDto(entity);
    }

    @Transactional
    @Override
    public List<WorkIntervalResponseDto> updateAll(Set<WorkIntervalUpdateDto> dtoList) {
        List<WorkIntervalResponseDto> responseList = new ArrayList<>();
        for (WorkIntervalUpdateDto dto: dtoList) {
            WorkIntervalEntity entity = repository.findById(dto.id()).orElseThrow(WorkIntervalNotFoundByIdException::new);
            mapper.updateEntityFromDto(dto, entity);
            responseList.add(mapper.toDto(entity));
        }
        return responseList;
    }

    @Transactional
    @Override
    public WorkIntervalResponseDto update(WorkIntervalUpdateDto dto) {
        WorkIntervalEntity entity = repository.findById(dto.id()).orElseThrow(WorkIntervalNotFoundByIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public WorkIntervalResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(WorkIntervalNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findAllEntityIdsByOwnerId(UUID ownerId) {
        return repository.findAllIdsByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    @Override
    public long countOwnedByOwnerId(UUID ownerId, List<Long> ids) {
        return repository.countOwnedByOwnerId(ownerId, ids);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkIntervalResponseDto> findAllByDate(LocalDate date) {
        List<WorkIntervalEntity> entityList = repository.findAllByDate(date);
        return mapper.toDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkIntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end) {
        List<WorkIntervalEntity> entityList = repository.findAllByOwnerIdAndDateInterval(ownerId, start, end);
        return mapper.toDtoList(entityList);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        repository.deleteAllByOwnerIdAndDate(ownerId, date);
    }
}
