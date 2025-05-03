package com.aidcompass.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CloudStorage {

    String save(UUID userId, MultipartFile image);

    String get(String url);

    void delete(String url);
}
