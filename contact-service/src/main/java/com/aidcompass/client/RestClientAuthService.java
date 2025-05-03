package com.aidcompass.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
public class RestClientAuthService implements AuthService {

    private final RestClient restClient;


    @Override
    public boolean existsById(UUID ownerId) {
        return true;
    }
}
