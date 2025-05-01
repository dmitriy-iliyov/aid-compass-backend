package com.aidcompass.avatar;

import com.aidcompass.avatar.cloud.AvatarCloudStorage;
import com.aidcompass.avatar.exceptions.AvatarNotFoundByUserIdException;
import com.aidcompass.avatar.model.AvatarEntity;
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
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUrl();

        return new FileSystemResource("");
    }

    @Override
    public String findBase64ByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUrl();

        return "";
    }

    @Transactional
    @Override
    public void deleteByUserId(UUID userId) {
        AvatarEntity entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
        cloudStorage.delete(entity.getUrl());
        repository.deleteById(entity.getId());
    }

    @Override
    public String findUrlByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUrl();
    }
}
