package com.aidcompass.cloud;

import com.aidcompass.model.AvatarInfo;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AzureBlobPrivateCloudStorage implements PrivateCloudStorage {

    private final BlobContainerClient blobContainerClient;


    @Override
    public AvatarInfo saveOrUpdate(UUID userId, MultipartFile image) {
        try {
            String originalFilename = Optional.ofNullable(image.getOriginalFilename()).orElse("file");
            if (originalFilename.length() > 100) {
                originalFilename = originalFilename.substring(0, 50);
            }
            String sanitizedFilename = originalFilename.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9._-]", "");
            String blobName = userId + "-" + sanitizedFilename;
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

            blobClient.upload(image.getInputStream(), image.getSize(), true);

            return new AvatarInfo(blobName, generateSasLink(blobName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Azure Blob Storage", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

            if (blobClient.exists()) {
                blobClient.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from Azure Blob Storage", e);
        }
    }

    @Override
    public String generateSasLink(String fileName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        BlobSasPermission permissions = new BlobSasPermission().setReadPermission(true);
        BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(
                OffsetDateTime.now().plusDays(1),
                permissions
        );
        String sasToken = blobClient.generateSas(values);
        return blobClient.getBlobUrl() + "?" + sasToken;
    }
}
