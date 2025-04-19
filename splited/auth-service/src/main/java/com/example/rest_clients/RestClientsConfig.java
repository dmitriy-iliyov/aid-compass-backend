package com.example.rest_clients;

import com.example.rest_clients.confirmation.RestClientConfirmationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientsConfig {

    @Value("{aid.compass.user.api.confirmation.service.url}")
    private String CONFIRMATION_SERVICE_URL;


    @Bean
    public RestClientConfirmationService confirmationRestClient() {
        return new RestClientConfirmationService(
                RestClient.builder()
                        .baseUrl(CONFIRMATION_SERVICE_URL)
                        .build());
    }
}
