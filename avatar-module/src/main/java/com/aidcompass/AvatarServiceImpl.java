package com.aidcompass;

import com.aidcompass.cloud.CloudStorage;
import com.aidcompass.models.PassedListIsToLongException;
import com.aidcompass.uuid.UuidUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository repository;
    private final CloudStorage cloudStorage;
    private final CacheManager cacheManager;


    @Transactional
    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        String url = cloudStorage.saveOrUpdate(userId, image);
        repository.save(new AvatarEntity(userId, url));
        return url;
    }

    @Override
    public String saveOrUpdateDefault(MultipartFile image) {
        return cloudStorage.saveOrUpdateDefault(image);
    }

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
        List<UUID> entityList = repository.findAllByUserIdIn(userIdList).stream().map(AvatarEntity::getUserId).toList();
        for (UUID id : userIdList) {
            if (entityList.contains(id)) {
                urls.put(id, cloudStorage.generateUrlById(id));
            } else {
                urls.put(id, cloudStorage.getDefaultUrl());
            }
        }

        if (cache != null) {
            hash = UuidUtils.hashUuidCollection(userIdList);
            Map<String, String> preparedToCache = urls.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue)
                    );
            cache.put(hash, preparedToCache);
        }
        return urls;
    }

    @Override
    public String findUrlByUserId(UUID userId) {
        return cloudStorage.generateUrlById(userId);
    }

    @Override
    public void delete(UUID userId) {
        cloudStorage.delete(userId);
        repository.deleteByUserId(userId);
    }
}
