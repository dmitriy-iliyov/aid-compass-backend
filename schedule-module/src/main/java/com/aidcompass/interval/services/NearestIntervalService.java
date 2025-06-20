package com.aidcompass.interval.services;

import com.aidcompass.exceptions.interval.NearestIntervalNotFoundByOwnerIdException;
import com.aidcompass.interval.mapper.NearestIntervalMapper;
import com.aidcompass.interval.models.NearestIntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.aidcompass.interval.models.dto.SystemIntervalDto;
import com.aidcompass.interval.repository.NearestIntervalRepository;
import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.PassedListIsToLongException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NearestIntervalService {

    private final NearestIntervalRepository repository;
    private final NearestIntervalMapper mapper;
    private final SystemIntervalService service;


    public Map<UUID, NearestIntervalDto> findAll(List<UUID> ownerIds) {
        if (ownerIds.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Map<UUID, NearestIntervalDto> response = mapper.toDtoList(repository.findAllById(ownerIds)).stream()
                .collect(Collectors.toMap(NearestIntervalDto::ownerId, Function.identity()));
        List<UUID> localOwnerIds = new ArrayList<>(ownerIds);
        localOwnerIds.removeAll(response.keySet());
        if (!localOwnerIds.isEmpty()) {
            for (UUID id: localOwnerIds) {
                try {
                    SystemIntervalDto dto = service.systemFindNearestByOwnerId(id);
                    response.put(id, saveWithTtl(mapper.toEntity(dto)));
                } catch (BaseNotFoundException ignored) { }
            }
        }
        return response;
    }

    public NearestIntervalDto findByOwnerId(UUID ownerId) {
        try {
            return mapper.toDto(repository.findById(ownerId).orElseThrow(
                    NearestIntervalNotFoundByOwnerIdException::new)
            );
        } catch (BaseNotFoundException e) {
            try {
                SystemIntervalDto dto = service.systemFindNearestByOwnerId(ownerId);
                return saveWithTtl(mapper.toEntity(dto));
            } catch (BaseNotFoundException e2) {
                return null;
            }
        }
    }

    public void deleteByOwnerId(UUID ownerId, Long id) {
        NearestIntervalDto currentNearest = mapper.toDto(repository.findById(ownerId).orElseThrow(
                NearestIntervalNotFoundByOwnerIdException::new)
        );
        if (currentNearest.id().equals(id)) {
            repository.deleteById(ownerId);
            try {
                SystemIntervalDto dto = service.systemFindNearestByOwnerId(ownerId);
                NearestIntervalEntity entity = mapper.toEntity(dto);
                saveWithTtl(entity);
            } catch (BaseNotFoundException ignored) { }
        }
    }

    public void replaceIfEarlier(UUID ownerId, IntervalResponseDto dto) {
        try {
            NearestIntervalDto currentNearest = mapper.toDto(repository.findById(ownerId).orElseThrow(
                    NearestIntervalNotFoundByOwnerIdException::new)
            );
            if (currentNearest.date().isAfter(dto.date()) || currentNearest.start().isAfter(dto.start())) {
                saveWithTtl(mapper.toEntity(dto));
            }
        } catch (BaseNotFoundException ignore) {
            saveWithTtl(mapper.toEntity(dto));
        }
    }

    private NearestIntervalDto saveWithTtl(NearestIntervalEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime;

        expirationTime = entity.getDate().atTime(LocalTime.of(10, 0)).minusDays(1);
        long ttlSeconds = Duration.between(now, expirationTime).getSeconds();

        if (ttlSeconds > 0) {
            entity.setTtlSeconds(ttlSeconds);
            return mapper.toDto(repository.save(entity));
        }
        return null;
    }
}
