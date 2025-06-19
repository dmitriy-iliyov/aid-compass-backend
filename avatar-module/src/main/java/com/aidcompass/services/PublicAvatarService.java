package com.aidcompass.services;

import com.aidcompass.uuid.UuidUtils;
import com.aidcompass.cloud.PublicCloudStorage;
import com.aidcompass.models.PassedListIsToLongException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.aidcompass.uuid.UuidUtils.hashUuidCollection;

@Service
public class PublicAvatarService implements AvatarService {

    private final PublicCloudStorage cloudStorage;
    private final CacheManager cacheManager;


    public PublicAvatarService(@Qualifier("azureBlobPublicCloudStorage") PublicCloudStorage cloudStorage,
                               CacheManager cacheManager) {
        this.cloudStorage = cloudStorage;
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "avatars:url", key = "#userId", unless = "#result == null")
    @Transactional
    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        return cloudStorage.saveOrUpdate(userId, image);
    }

    @Transactional
    @Override
    public Map<UUID, String> findAllUrlByOwnerIdIn(List<UUID> userIdList) {
        if (userIdList.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Cache cache = cacheManager.getCache("avatars:url:map");
        String hash;
        if (cache != null ) {
            hash = hashUuidCollection(userIdList);
            Map<String, String> fromCache = cache.get(hash, Map.class);
            if (fromCache != null) {
                return fromCache.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UUID.fromString(e.getKey()),
                                Map.Entry::getValue)
                        );
            }
        }

        Map<UUID, String> urls = new HashMap<>();
        for (UUID id : userIdList) {
            urls.put(id, cloudStorage.findById(id));
        }

        if (cache != null && !urls.containsValue(null)) {
            hash = UuidUtils.hashUuidCollection(userIdList);
            Map<String, String> preparedToCache = urls.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue)
                    );
            cache.put(hash, preparedToCache);
        }
        return urls;
    }

    @Cacheable(value = "avatars:url", key = "#userId", unless = "#result == null")
    @Transactional
    @Override
    public String findUrlByUserId(UUID userId) {
        return cloudStorage.findById(userId);
    }

    @CacheEvict(value = "avatars:url", key = "#userId")
    @Transactional
    @Override
    public void delete(UUID userId) {
        cloudStorage.delete(userId);
    }
}
