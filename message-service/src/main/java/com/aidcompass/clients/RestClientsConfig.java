package com.aidcompass.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientsConfig {

    @Value("${api.auth.uri}")
    private String AUTH_SERVICE_URL;

    @Value("${api.contacts.uri}")
    private String CONTACTS_SERVICE_URL;


    @Bean
    public RestClientAuthService restClientAuthService() {
        return new RestClientAuthService(RestClient.builder()
                .baseUrl(AUTH_SERVICE_URL)
                .build());
    }

    @Bean
    public RestClientContactService restClientContactsService() {
        return new RestClientContactService(RestClient.builder()
                .baseUrl(CONTACTS_SERVICE_URL)
                .build());
    }
}
