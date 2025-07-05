package com.aidcompass.appointment.services;

import com.aidcompass.general.GlobalRedisConfig;
import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.models.dto.StatusFilter;
import com.aidcompass.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import com.aidcompass.appointment.repositories.AppointmentRepository;
import com.aidcompass.appointment.repositories.AppointmentSpecifications;
import com.aidcompass.appointment.mapper.AppointmentMapper;
import com.aidcompass.general.contracts.dto.BatchResponse;
import com.aidcompass.general.contracts.dto.PageResponse;
import com.aidcompass.exceptions.appointment.AppointmentNotFoundByIdException;
import com.aidcompass.exceptions.appointment.AppointmentOwnershipException;
import com.aidcompass.appointment.models.AppointmentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedAppointmentService implements AppointmentService, SystemAppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final CacheManager cacheManager;


    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
                            key = "#dto.volunteerId() + ':' + #dto.date() + ':SCHEDULED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#dto.volunteerId()")
            }
    )
    @Transactional
    @Override
    public AppointmentResponseDto save(UUID customerId, LocalTime end, AppointmentCreateDto dto) {
        AppointmentEntity entity = mapper.toEntity(customerId, end, dto);
        entity.setStatus(AppointmentStatus.SCHEDULED);
        return mapper.toDto(repository.save(entity));
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
                            key = "#dto.getVolunteerId() + ':' + #dto.getDate() + ':SCHEDULED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#dto.getVolunteerId()"),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_CACHE_NAME, key = "#dto.getId()")
            }
    )
    @Transactional
    @Override
    public Map<AppointmentAgeType, AppointmentResponseDto> update(UUID customerId, LocalTime end, AppointmentUpdateDto dto) {
        Map<AppointmentAgeType, AppointmentResponseDto> responseMap = new HashMap<>();
        AppointmentEntity entity = repository.findById(dto.getId()).orElseThrow(AppointmentNotFoundByIdException::new);
        responseMap.put(AppointmentAgeType.OLD, mapper.toDto(entity));
        if (customerId.equals(entity.getCustomerId())) {
            mapper.updateEntityFromUpdateDto(dto, end, entity);
            entity = repository.save(entity);
            responseMap.put(AppointmentAgeType.NEW, mapper.toDto(entity));
            return responseMap;
        }
        throw new AppointmentOwnershipException();
    }

    @Cacheable(value = GlobalRedisConfig.APPOINTMENTS_CACHE_NAME, key = "#id")
    @Transactional(readOnly = true)
    @Override
    public AppointmentResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AppointmentResponseDto> findAllByStatusFilter(UUID participantId, StatusFilter filter,
                                                                      int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").and(Sort.by("start")).ascending());

        Specification<AppointmentEntity> specification = Specification
                .where(AppointmentSpecifications.hasParticipantId(participantId))
                .and(AppointmentSpecifications.hasStatuses(filter));

        Page<AppointmentEntity> entityPage = repository.findAll(specification, pageable);

        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
            key = "#volunteerId + ':' + #date + ':' + #status"
    )
    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date,
                                                                             AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllByVolunteerIdAndDateAndStatus(volunteerId, date, status));
    }

    @Cacheable(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#volunteerId")
    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllByVolunteerIdAndDateInterval(UUID volunteerId, LocalDate start, LocalDate end) {
        List<AppointmentEntity> entityList = repository.findAllByVolunteerIdAndDateInterval(volunteerId, start, end);
        return mapper.toDtoList(entityList);
    }

    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_CACHE_NAME, key = "#id")

    @Transactional
    @Override
    public void markCompletedById(Long id, String review) {
        repository.updateStatusAndSetReview(id, review, AppointmentStatus.COMPLETED);
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
                            key = "#result.volunteerId() + ':' + #result.date() + ':' + #result.status()"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#result.volunteerId()")
            },
            put = @CachePut(value = GlobalRedisConfig.APPOINTMENTS_CACHE_NAME, key = "#id")
    )
    @Transactional
    @Override
    public AppointmentResponseDto markCanceledById(Long id) {
        AppointmentEntity entity = repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new);
        AppointmentResponseDto dto = mapper.toDto(entity);
        repository.updateStatus(dto.id(), AppointmentStatus.CANCELED);
        return dto;
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
                            key = "#participantId + ':' + #date + ':CANCELED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#participantId")
            }
    )
    @Transactional
    @Override
    public void markCanceledAllByDate(UUID participantId, LocalDate date) {
        repository.updateAllStatus(participantId, date, AppointmentStatus.CANCELED);
    }

//    @CacheEvict(
//            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE_NAME,
//            password = "#participantId + ':' + #date + ':CANCELED'"
//    ),
    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE_NAME, key = "#participantId")
    @Transactional
    @Override
    public void deleteAll(UUID participantId) {
        List<Long> ids = repository.deleteAllByParticipantId(participantId);
        Cache cache = cacheManager.getCache(GlobalRedisConfig.APPOINTMENTS_CACHE_NAME);
        if (cache != null) {
            for (Long id: ids) {
                cache.evict(id);
            }
        }
    }

    @Override
    public List<AppointmentResponseDto> findAllByCustomerIdAndDateAndStatus(UUID customerId, LocalDate date, AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllByCustomerIdAndDateAndStatus(customerId, date, status));
    }

    // invalidate all caches
    @Transactional
    @Override
    public List<Long> markBatchAsSkipped(int batchSize) {
        LocalDate dateLimit = LocalDate.now().minusDays(1);
        log.info("START marking appointments as skipped with batchSize={}, dateLimit={}", batchSize, dateLimit);
        List<Long> markedIds = repository.markBatchAsSkipped(batchSize, dateLimit);
        log.info("END marking appointments, marked id list={}", markedIds);
        return markedIds;
    }

    @Transactional(readOnly = true)
    @Override
    public BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page) {
        LocalDate runDate = LocalDate.now().plusDays(1);
        log.info("START selecting appointments to remind with batchSize={}, page={}, runDate={}", batchSize, page, runDate);
        Slice<AppointmentEntity> slice = repository.findBatchToRemind(
                runDate,
                Pageable.ofSize(batchSize).withPage(page)
        );
        log.info("END selecting, hasNext={}", slice.hasNext());
        return new BatchResponse<>(
                mapper.toDtoList(slice.getContent()),
                slice.hasNext()
        );
    }
}
