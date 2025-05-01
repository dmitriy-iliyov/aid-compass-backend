package com.example.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class RestClientConfirmationService implements ConfirmationService {

    private final RestClient restClient;


    @Override
    public void sendConfirmationMessage(String recipientResource) {

    }

    @Override
    public void validateConfirmationToken(String inputToken) {

    }
}
