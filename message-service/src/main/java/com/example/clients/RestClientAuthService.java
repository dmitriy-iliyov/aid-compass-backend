package com.example.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
public class RestClientAuthService implements AuthService {

    private final RestClient restClient;

    @Override
    public void confirmByEmail(String email) {
        restClient.patch()
                .uri("/confirm/email?email=", email)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void recoverPassword(String email, String password) {
        restClient.patch()
                .uri("/recover-password/email")
                .body(new RecoveryRequestDto(email, password))
                .retrieve()
                .toBodilessEntity();
    }
}
