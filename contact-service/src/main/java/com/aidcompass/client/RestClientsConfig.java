package com.aidcompass.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientsConfig {

    @Value("${aid.compass.api.auth.uri}")
    private String AUTH_SERVICE_URI;

    @Value("${aid.compass.api.confirm.uri}")
    private String MESSAGE_SERVICE_URI;


    @Bean
    public RestClientAuthService authRestClient() {
        return new RestClientAuthService(
                RestClient.builder()
                        .baseUrl(AUTH_SERVICE_URI)
                        .build());
    }

    @Bean
    public RestClientConfirmationService confirmationRestClient() {
        return new RestClientConfirmationService(
                RestClient.builder()
                        .baseUrl(MESSAGE_SERVICE_URI)
                        .build());
    }
}
