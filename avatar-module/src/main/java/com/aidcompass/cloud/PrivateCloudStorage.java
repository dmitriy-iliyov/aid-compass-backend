package com.aidcompass.cloud;

import com.aidcompass.model.AvatarInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PrivateCloudStorage {
    AvatarInfo saveOrUpdate(UUID userId, MultipartFile image);

    String generateSasLink(String fileName);

    void delete(String fileName);
}
