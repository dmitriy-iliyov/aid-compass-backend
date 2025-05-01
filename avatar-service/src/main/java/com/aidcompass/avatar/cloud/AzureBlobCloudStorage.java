package com.aidcompass.avatar.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AzureBlobCloudStorage implements AvatarCloudStorage {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

//    private final BlobServiceClient blobServiceClient;


    @Override
    public String save(UUID userId, MultipartFile image) {
        return "";
    }

    @Override
    public String get(String url) {
        return "";
    }

    @Override
    public void delete(String url) {

    }
}
