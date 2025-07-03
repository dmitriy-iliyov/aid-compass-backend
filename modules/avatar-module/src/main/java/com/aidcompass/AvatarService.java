package com.aidcompass;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AvatarService {

    String saveOrUpdate(UUID userId, MultipartFile image);

    String saveOrUpdateDefault(MultipartFile image);

    Map<UUID, String> findAllUrlByOwnerIdIn(List<UUID> userIdList);

    String findUrlByUserId(UUID userId);

    void delete(UUID userId);
}
