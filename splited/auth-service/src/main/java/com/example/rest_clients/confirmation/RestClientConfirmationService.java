package com.example.rest_clients.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
public class RestClientConfirmationService implements ConfirmationService {

    private final RestClient restClient;

    @Async
    @Override
    public void sendConfirmationMessage(String email) {
        restClient.post()
                .uri("/request?resource=", email)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }
}
