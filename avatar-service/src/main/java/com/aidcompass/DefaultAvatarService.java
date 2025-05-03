package com.aidcompass;

import com.aidcompass.cloud.CloudStorage;
import com.aidcompass.exceptions.AvatarNotFoundByUserIdException;
import com.aidcompass.model.AvatarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAvatarService implements AvatarService {

    private final AvatarRepository repository;
    private final CloudStorage cloudStorage;


    @Transactional
    @Override
    public void create(UUID userId, MultipartFile image) {
        String url = cloudStorage.save(userId, image);
        repository.save(new AvatarEntity(userId, url));
    }

    @Override
    public Resource findBytesByUserId(UUID userId) {
        String uri = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUri();
        return new FileSystemResource("");
    }

    @Override
    public String findBase64ByUserId(UUID userId) {
        String uri = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUri();
        return "";
    }

    @Transactional
    @Override
    public void deleteByUserId(UUID userId) {
        AvatarEntity entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
        cloudStorage.delete(entity.getUri());
        repository.deleteById(entity.getId());
    }

    @Override
    public String findUriByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getUri();
    }
}
