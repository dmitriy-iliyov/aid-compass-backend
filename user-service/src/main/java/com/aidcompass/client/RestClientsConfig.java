package com.aidcompass.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


//@Configuration
//public class RestClientsConfig {
//
//    @Value("${aid.compass.api.auth.url}")
//    private String AUTH_SERVICE_URL;
//
//    @Value("${aid.compass.api.message.url}")
//    private String MESSAGE_SERVICE_URL;
//
//    @Bean
//    public RestClientAuthService authRestClient() {
//        return new RestClientAuthService(
//                RestClient.builder()
//                        .baseUrl(AUTH_SERVICE_URL)
//                        .build());
//    }
//
//    @Bean
//    public RestClientConfirmationService messageRestClient() {
//        return new RestClientConfirmationService(
//                RestClient.builder()
//                        .baseUrl(MESSAGE_SERVICE_URL)
//                        .build());
//    }
//
//}
