package com.aidcompass.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PublicCloudStorage {
    String saveOrUpdate(UUID userId, MultipartFile image);

    String findById(UUID userId);

    void delete(UUID userId);
}
