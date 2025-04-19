package com.example.rest_clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientsConfig {

    @Value("${aid.compass.messaging.api.user.service.uri}")
    private String USER_SERVICE_URL;


    @Bean
    public RestClientUserService restClientUserService() {
        return new RestClientUserService(RestClient.builder()
                .baseUrl(USER_SERVICE_URL)
                .build());
    }
}
