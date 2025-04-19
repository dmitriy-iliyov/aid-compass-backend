package com.example;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AvatarCloudStorageImpl implements AvatarCloudStorage {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    private final BlobServiceClient blobServiceClient;


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
