package com.aidcompass.interval.services;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.exceptions.interval.IntervalNotFoundByIdException;
import com.aidcompass.interval.mapper.IntervalMapper;
import com.aidcompass.interval.models.IntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.dto.SystemIntervalDto;
import com.aidcompass.interval.models.dto.SystemIntervalUpdateDto;
import com.aidcompass.interval.models.overlaps.ValidationInfoDto;
import com.aidcompass.interval.repository.IntervalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedIntervalService implements IntervalService, SystemIntervalService {

    private final IntervalRepository repository;
    private final IntervalMapper mapper;


    @Transactional
    @Override
    public IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto) {
        IntervalEntity entity = repository.save(mapper.toEntity(ownerId, dto));
        return mapper.toDto(entity);
    }

    @Transactional
    @Override
    public IntervalResponseDto save(UUID ownerId, ValidationInfoDto info) {
        IntervalEntity entity = repository.save(mapper.toEntity(ownerId, info.dto()));
        repository.deleteAllById(info.toDeleteIdList());
        return mapper.toDto(entity);
    }

    @Transactional
    @Override
    public List<IntervalResponseDto> saveAll(UUID ownerId, Set<SystemIntervalCreatedDto> dtoSet) {
        List<IntervalEntity> entityList = repository.saveAll(mapper.toEntityList(ownerId, dtoSet));
        return mapper.toDtoList(entityList).stream()
                .sorted(Comparator.comparing(IntervalResponseDto::start))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public long countOwnedByOwnerId(UUID ownerId, List<Long> ids) {
        return repository.countOwnedByOwnerId(ownerId, ids);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemIntervalDto systemFindById(Long id) {
        return mapper.toSystemDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public SystemIntervalDto systemFindNearestByOwnerId(UUID ownerId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.with(TemporalAdjusters.lastDayOfMonth());
        return mapper.toSystemDto(repository.findFirstByOwnerIdAndDateBetweenOrderByDateAscStartAsc(ownerId, start, end.toLocalDate())
                .orElseThrow(IntervalNotFoundByIdException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public boolean findByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date) {
        return repository.existsByOwnerIdAndStartAndDate(ownerId, start, date);
    }

    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDate(ownerId, date);
        return mapper.toDtoList(entityList).stream()
                .sorted(Comparator.comparing(IntervalResponseDto::start))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemIntervalDto> systemFindAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDate(ownerId, date);
        return mapper.toSystemDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDateInterval(ownerId, start, end);
        return mapper.toDtoList(entityList);
    }

    @Transactional
    @Override
    public Set<IntervalResponseDto> cut(AppointmentMarker dto, LocalTime end, Long id) {
        IntervalEntity entity = repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new);
        SystemIntervalDto systemDto = mapper.toSystemDto(entity);

        Set<IntervalResponseDto> responseDtoSet = new HashSet<>();
        SystemIntervalUpdateDto updateDto;
        if (systemDto.start().equals(dto.start()) && systemDto.end().isAfter(end)) {
            updateDto = new SystemIntervalUpdateDto(id, end, systemDto.end(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.add(mapper.toDto(repository.save(entity)));
        } else if (systemDto.end().equals(end) && systemDto.start().isBefore(dto.start())) {
            updateDto = new SystemIntervalUpdateDto(id, systemDto.start(), dto.start(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.add(mapper.toDto(repository.save(entity)));
        } else {
            updateDto = new SystemIntervalUpdateDto(id, systemDto.start(), dto.start(), systemDto.date());
            IntervalEntity newIntervalEntity = new IntervalEntity(entity.getOwnerId(), end, systemDto.end(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.addAll(mapper.toDtoList(repository.saveAll(List.of(entity, newIntervalEntity))));
        }
        return responseDtoSet;
    }

    @Transactional
    @Override
    public void deleteByOwnerIdAndId(UUID ownerId, Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        repository.deleteAllByOwnerIdAndDate(ownerId, date);
    }

    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        repository.deleteAllByOwnerId(ownerId);
    }
}
