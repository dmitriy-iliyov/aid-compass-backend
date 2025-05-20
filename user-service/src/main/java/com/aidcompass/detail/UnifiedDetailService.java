package com.aidcompass.detail;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.ServiceType;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.exceptions.DetailEntityNotFoundByUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedDetailService implements DetailService, PersistEmptyDetailService {

    private final DetailRepository repository;
    private final DetailMapper mapper;
    private static final String CACHE_NAME_TEMPLATE = "%s:public:full";
    private final CacheManager cache;

    // на основе роли решать какой сервис использовать для того чтоб установить зависимость?

    @Transactional
    @Override
    public DetailEntity saveEmpty(UUID userId) {
        return repository.save(new DetailEntity(userId));
    }

    @Deprecated
    @Transactional
    @Override
    public PrivateDetailResponseDto update(UUID userId, DetailDto dto) {
        DetailEntity entity = repository.findByUserId(userId).orElseThrow(DetailEntityNotFoundByUserIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        entity = repository.save(entity);
        return mapper.toPrivateDetailDto(entity);
    }

    @Transactional
    @Override
    public PrivateDetailResponseDto updateWithCache(UUID userId, DetailDto dto, ServiceType serviceType) {
        DetailEntity entity = repository.findByUserId(userId).orElseThrow(DetailEntityNotFoundByUserIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        entity = repository.save(entity);

        String cacheName = CACHE_NAME_TEMPLATE.formatted(serviceType.toString());
        Objects.requireNonNull(cache.getCache(cacheName)).evict(userId);

        return mapper.toPrivateDetailDto(entity);
    }
}
