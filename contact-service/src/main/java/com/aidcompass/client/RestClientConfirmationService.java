package com.aidcompass.client;

import com.aidcompass.client.models.ConfirmationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class RestClientConfirmationService implements ConfirmationService {

    private final RestClient restClient;


    @Override
    public void sendConfirmationRequest(ConfirmationRequestDto request) {

    }
}
