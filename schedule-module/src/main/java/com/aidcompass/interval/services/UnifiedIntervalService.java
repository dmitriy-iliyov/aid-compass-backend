package com.aidcompass.interval.services;

import com.aidcompass.GlobalRedisConfig;
import com.aidcompass.exceptions.interval.IntervalNotFoundByIdException;
import com.aidcompass.interval.mapper.IntervalMapper;
import com.aidcompass.interval.models.IntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.dto.SystemIntervalUpdateDto;
import com.aidcompass.interval.repository.IntervalRepository;
import com.aidcompass.appointment.models.marker.AppointmentMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
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
public class UnifiedIntervalService implements IntervalService {

    private final IntervalRepository repository;
    private final IntervalMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;


    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME,
                                key = "#ownerId + ':' + #result.date()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME, key = "#ownerId"),
            }
    )
    @Transactional
    @Override
    public IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto) {
        IntervalEntity entity = repository.save(mapper.toEntity(ownerId, dto));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findNearestByOwnerId(UUID ownerId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.with(TemporalAdjusters.lastDayOfMonth());
        return mapper.toDto(repository.findFirstByOwnerIdAndDateBetweenOrderByDateAscStartAsc(ownerId, start, end.toLocalDate())
                .orElseThrow(IntervalNotFoundByIdException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date) {
        return mapper.toDto(repository.findByOwnerIdAndStartAndDate(ownerId, start, date).orElseThrow(
                IntervalNotFoundByIdException::new)
        );
    }

    @Cacheable(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME, key = "#ownerId + ':' + #date")
    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDate(ownerId, date);
        return mapper.toDtoList(entityList).stream()
                .sorted(Comparator.comparing(IntervalResponseDto::start))
                .toList();
    }

    @Cacheable(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME, key = "#ownerId")
    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDateInterval(ownerId, start, end);
        return mapper.toDtoList(entityList);
    }


    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME,
                            key = "#dto.volunteerId() + ':' + #dto.date()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME, key = "#dto.volunteerId()"),
            }
    )
    @Transactional
    @Override
    public Set<IntervalResponseDto> cut(AppointmentMarker dto, LocalTime end, Long id) {
        IntervalEntity entity = repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new);
        IntervalResponseDto systemDto = mapper.toDto(entity);

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

    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME,
                            key = "#result.ownerId() + ':' + #result.date()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME, key = "#result.ownerId()"),
            }
    )
    @Transactional
    @Override
    public IntervalResponseDto deleteByOwnerIdAndId(UUID ownerId, Long id) {
        IntervalResponseDto dto = mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
        repository.deleteById(id);
        return dto;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME,
                            key = "#ownerId + ':' + #date"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME, key = "#ownerId"),
            }
    )
    @Transactional
    @Override
    public void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        repository.deleteAllByOwnerIdAndDate(ownerId, date);
    }

    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        repository.deleteAllByOwnerId(ownerId);

        // scan
        redisTemplate.delete(
                Objects.requireNonNull(
                        redisTemplate.keys(GlobalRedisConfig.INTERVALS_BY_DATE_CACHE_NAME + ':' + ownerId + ":*"))
        );
        redisTemplate.delete(
                Objects.requireNonNull(
                        redisTemplate.keys(GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE_NAME + ':' + ownerId))
        );

    }
}
