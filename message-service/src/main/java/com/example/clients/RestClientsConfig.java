package com.example.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientsConfig {

    @Value("${aid.compass.api.auth.url}")
    private String AUTH_SERVICE_URL;


    @Bean
    public RestClientAuthService restClientUserService() {
        return new RestClientAuthService(RestClient.builder()
                .baseUrl(AUTH_SERVICE_URL)
                .build());
    }
}
