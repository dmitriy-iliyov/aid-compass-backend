package com.aidcompass.appointment_duration;

import com.aidcompass.GlobalRedisConfig;
import com.aidcompass.appointment_duration.models.AppointmentDurationEntity;
import com.aidcompass.enums.Authority;
import com.aidcompass.exceptions.appointment_duration.DurationNotFoundByOwnerIdException;
import com.aidcompass.PassedListIsToLongException;
import com.aidcompass.schedule_filling_progress.ScheduleProgressService;
import com.aidcompass.uuid.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class  AppointmentDurationServiceImpl implements AppointmentDurationService {

    private final AppointmentDurationRepository repository;
    private final ScheduleProgressService scheduleProgressService;
    private final CacheManager cacheManager;


    @CachePut(value = GlobalRedisConfig.APPOINTMENT_DURATION_CACHE_NAME, key = "#ownerId")
    @Transactional
    @Override
    public Long setAppointmentDuration(UUID ownerId, Authority authority, Long duration) {
        try {
            AppointmentDurationEntity entity = repository.findByOwnerId(ownerId).orElseThrow(DurationNotFoundByOwnerIdException::new);
            entity.setAppointmentDuration(duration);
            duration = repository.save(entity).getAppointmentDuration();
        } catch (DurationNotFoundByOwnerIdException e) {
            AppointmentDurationEntity entity = repository.save(new AppointmentDurationEntity(ownerId, duration));
            scheduleProgressService.appointmentDurationFilled(ownerId, authority);
            duration = entity.getAppointmentDuration();
        }
        return duration;
    }

    @Cacheable(value = GlobalRedisConfig.APPOINTMENT_DURATION_CACHE_NAME, key = "#ownerId", unless = "#result == null")
    @Transactional(readOnly = true)
    @Override
    public Long findAppointmentDurationByOwnerId(UUID ownerId) {
        Cache cache = cacheManager.getCache(GlobalRedisConfig.APPOINTMENT_DURATION_CACHE_NAME);
        Number duration = Objects.requireNonNull(cache).get(ownerId, Number.class);
        if (duration != null) {
            return duration.longValue();
        }
        return repository
                .findByOwnerId(ownerId)
                .orElseThrow(DurationNotFoundByOwnerIdException::new)
                .getAppointmentDuration();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, Long> findAllByOwnerIdIn(List<UUID> ownerIds) {
        if (ownerIds.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Cache cache = cacheManager.getCache(GlobalRedisConfig.APPOINTMENT_DURATION_MAP_CACHE_NAME);
        String hash = UuidUtils.hashUuidCollection(ownerIds);
        if (cache != null) {
            Map<String, String> fromCache = cache.get(hash, Map.class);
            if (fromCache != null) {
                return fromCache.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UUID.fromString(e.getKey()),
                                e -> Long.valueOf(e.getValue()))
                        );
            }
        }
        Map<UUID, Long> responseMap = new HashMap<>();
        List<AppointmentDurationEntity> entityList = repository.findAllByOwnerIdIn(ownerIds);
        entityList.forEach(entity -> responseMap.put(entity.getOwnerId(), entity.getAppointmentDuration()));

        if (cache != null) {
            Map<String, String> preparedToCache = responseMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().toString())
                    );
            cache.put(hash, preparedToCache);
        }

        return responseMap;
    }

    @CacheEvict(value = GlobalRedisConfig.APPOINTMENT_DURATION_CACHE_NAME, key = "#ownerId")
    @Transactional(readOnly = true)
    @Override
    public void deleteAppointmentDurationByOwnerId(UUID ownerId) {
        repository.deleteByOwnerId(ownerId);
    }
}
