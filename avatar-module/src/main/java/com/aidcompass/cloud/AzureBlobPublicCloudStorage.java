package com.aidcompass.cloud;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AzureBlobPublicCloudStorage implements PublicCloudStorage {

    private final BlobContainerClient blobContainerClient;
    private final String FILE_NAME_TEMPLATE = "%s-avatar.jpg";


    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(FILE_NAME_TEMPLATE.formatted(userId));
            blobClient.upload(image.getInputStream(), image.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Azure Blob Storage!", e);
        }
    }

    @Override
    public String findById(UUID userId) {
        BlobClient blobClient = blobContainerClient.getBlobClient(FILE_NAME_TEMPLATE.formatted(userId));
        if (Boolean.TRUE.equals(blobClient.exists())) {
            return blobClient.getBlobUrl();
        }
        return null;
    }

    @Override
    public void delete(UUID userId) {
        BlobClient blobClient = blobContainerClient.getBlobClient(FILE_NAME_TEMPLATE.formatted(userId));
        blobClient.delete();
    }
}
