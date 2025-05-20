package com.aidcompass.cloud;

import com.azure.storage.blob.BlobServiceClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AzureBlobCloudStorage implements CloudStorage {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    private final BlobServiceClient blobServiceClient;


    @PostConstruct
    public void setUpBlobServiceClient() {
        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }


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
