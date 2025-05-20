package com.aidcompass;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarService {

    String create(UUID userId, MultipartFile image);

    Resource findBytesByUserId(UUID userId);

    String findBase64ByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    String findUriByUserId(UUID userId);
}
