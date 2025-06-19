package com.aidcompass.services;

import com.aidcompass.AvatarRepository;
import com.aidcompass.uuid.UuidUtils;
import com.aidcompass.cloud.PrivateCloudStorage;
import com.aidcompass.exceptions.AvatarNotFoundByUserIdException;
import com.aidcompass.models.PassedListIsToLongException;
import com.aidcompass.model.AvatarEntity;
import com.aidcompass.model.AvatarInfo;
import com.aidcompass.models.BaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateAvatarService implements AvatarService {

    private final AvatarRepository repository;
    private final PrivateCloudStorage privateCloudStorage;
    private final CacheManager cacheManager;


    @CachePut(value = "avatars:sas_link", key = "#userId")
    @Transactional
    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        AvatarInfo avatarInfo = privateCloudStorage.saveOrUpdate(userId, image);
        AvatarEntity entity;
        try {
             entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
             entity.setFileName(avatarInfo.fileName());
             entity.setSasLink(avatarInfo.sasLink());
        } catch (BaseNotFoundException e) {
            entity = new AvatarEntity(userId, avatarInfo.fileName(), avatarInfo.sasLink());
        }
        return repository.save(entity).getSasLink();
    }

    @Transactional
    @Override
    public Map<UUID, String> findAllUrlByOwnerIdIn(List<UUID> userIdList) {
        if (userIdList.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Cache cache = cacheManager.getCache("avatars:sas_link:map");
        String hash = UuidUtils.hashUuidCollection(userIdList);
        if (cache != null ) {
            Map<String, String> fromCache = cache.get(hash, Map.class);
            if (fromCache != null) {
                return fromCache.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UUID.fromString(e.getKey()),
                                Map.Entry::getValue)
                        );
            }
        }
        Map<UUID, String> sasLinks = new HashMap<>();
        List<AvatarEntity> entityList = repository.findAllByUserIdIn(userIdList);
        for (AvatarEntity entity : entityList) {
            if (Duration.between(entity.getUpdatedAt(), Instant.now()).compareTo(Duration.ofHours(1)) >= 0) {
                entity.setSasLink(privateCloudStorage.generateSasLink(entity.getFileName()));
                entity = repository.save(entity);
            }
            sasLinks.put(entity.getUserId(), entity.getSasLink());
        }
        if (cache != null) {
            Map<String, String> preparedToCache = sasLinks.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue)
                    );
            cache.put(hash, preparedToCache);
        }
        return sasLinks;
    }

    @Cacheable(value = "avatars:sas_link", key = "#userId")
    @Transactional
    @Override
    public String findUrlByUserId(UUID userId) {
        AvatarEntity entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
        if (Duration.between(entity.getUpdatedAt(), Instant.now()).compareTo(Duration.ofHours(1)) >= 0) {
            entity.setSasLink(privateCloudStorage.generateSasLink(entity.getFileName()));
            entity = repository.save(entity);
        }
        return entity.getSasLink();
    }

    @CacheEvict(value = "avatars:sas_link", key = "#userId")
    @Transactional
    @Override
    public void delete(UUID userId) {
        AvatarEntity entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
        privateCloudStorage.delete(entity.getFileName());
        repository.deleteById(entity.getId());
    }
}
