package com.example;

import com.example.clicker.avatar.exceptions.AvatarNotFoundByUserIdException;
import com.example.clicker.avatar.model.AvatarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository repository;
    private final AvatarCloudStorage cloudStorage;


    @Override
    public void create(UUID userId, MultipartFile image) {
        String url = cloudStorage.save(userId, image);
        repository.save(new AvatarEntity(userId, url));
    }

    @Override
    public Resource findBytesByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getAvatarUrl();

        return new FileSystemResource("");
    }

    @Override
    public String findBase64ByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getAvatarUrl();

        return "";
    }

    @Override
    public void deleteByUserId(UUID userId) {
        String url = repository.findById(userId)
                .orElseThrow(AvatarNotFoundByUserIdException::new)
                .getAvatarUrl();
        cloudStorage.delete(url);
    }
}
