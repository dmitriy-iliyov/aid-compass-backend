package com.example;

import com.example.cloud.AvatarCloudStorage;
import com.example.exceptions.AvatarBaseNotFoundByUserIdException;
import com.example.model.AvatarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository repository;
    private final AvatarCloudStorage cloudStorage;


    @Transactional
    @Override
    public void create(UUID userId, MultipartFile image) {
        String url = cloudStorage.save(userId, image);
        repository.save(new AvatarEntity(userId, url));
    }

    @Override
    public Resource findBytesByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarBaseNotFoundByUserIdException::new)
                .getUrl();

        return new FileSystemResource("");
    }

    @Override
    public String findBase64ByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarBaseNotFoundByUserIdException::new)
                .getUrl();

        return "";
    }

    @Transactional
    @Override
    public void deleteByUserId(UUID userId) {
        AvatarEntity entity = repository.findByUserId(userId).orElseThrow(AvatarBaseNotFoundByUserIdException::new);
        cloudStorage.delete(entity.getUrl());
        repository.deleteById(entity.getId());
    }
}
