package com.aidcompass.detail;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.enums.ServiceType;
import com.aidcompass.exceptions.UserAlreadyExistException;
import com.aidcompass.exceptions.detail.DetailEntityNotFoundByUserIdException;
import com.aidcompass.interfaces.ProfileStatusUpdateFacade;
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
    private final ProfileStatusUpdateFacade profileStatusUpdateFacade;
    private static final String CACHE_NAME_TEMPLATE = "%s:public:full";
    private final CacheManager cache;


    @Transactional
    @Override
    public DetailEntity saveEmpty(UUID userId) {
        if (repository.existsByUserId(userId)) {
            throw new UserAlreadyExistException();
        }
        DetailEntity entity = new DetailEntity(userId);
        entity = repository.save(entity);
        return entity;
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

        if (entity.getAboutMyself() != null && !entity.getAboutMyself().trim().isEmpty()) {
            profileStatusUpdateFacade.updateProfileStatus(serviceType, userId);
        }

        return mapper.toPrivateDetailDto(entity);
    }
}
