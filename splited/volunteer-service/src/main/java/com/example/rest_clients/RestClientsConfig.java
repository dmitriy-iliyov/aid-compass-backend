package com.example.rest_clients;


import com.example.rest_clients.avatar.RestClientAvatarService;
import com.example.rest_clients.user.RestClientUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientsConfig {

    @Value("{}")
    private String USER_SERVICE_URL;

    @Value("{}")
    private String AVATAR_SERVICE_URL;


    @Bean
    public RestClientUserService restClientUserService() {
        return new RestClientUserService(
                RestClient.builder()
                        .baseUrl(USER_SERVICE_URL)
                        .build()
        );
    }

    @Bean
    public RestClientAvatarService restClientAvatarService() {
        return new RestClientAvatarService(
                RestClient.builder()
                        .baseUrl(AVATAR_SERVICE_URL)
                        .build()
        );
    }
}
