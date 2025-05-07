package com.aidcompass.clients;

import com.aidcompass.clients.confirmation.RestClientConfirmationService;
import com.aidcompass.clients.contacts.RestClientContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientsConfig {

    @Value("${aid.compass.api.confirmation.url}")
    private String CONFIRMATION_SERVICE_URL;

    private String CONTACT_SERVICE_URL;


    @Bean
    public RestClientConfirmationService restClientConfirmationService() {
        return new RestClientConfirmationService(
                RestClient.builder()
                        .baseUrl(CONFIRMATION_SERVICE_URL)
                        .build());
    }

    @Bean
    public RestClientContactService restClientContactService() {
        return new RestClientContactService(
                RestClient.builder()
                        .baseUrl(CONTACT_SERVICE_URL)
                        .build()
        );
    }
}
