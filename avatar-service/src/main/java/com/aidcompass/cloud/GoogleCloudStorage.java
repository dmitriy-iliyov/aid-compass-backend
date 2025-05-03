package com.aidcompass.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GoogleCloudStorage implements CloudStorage {



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
