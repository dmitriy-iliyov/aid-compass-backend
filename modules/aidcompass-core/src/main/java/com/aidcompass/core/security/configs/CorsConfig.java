package com.aidcompass.core.security.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${server.allowed.origin}")
    public String origin;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration defaultConfig = new CorsConfiguration();
        defaultConfig.addAllowedOrigin(origin);
        defaultConfig.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        defaultConfig.setAllowedHeaders(List.of("*"));
        defaultConfig.setAllowCredentials(true);
        defaultConfig.setMaxAge(3600L);

        CorsConfiguration csrfConfig = new CorsConfiguration();
        csrfConfig.setAllowedOrigins(List.of(origin));
        csrfConfig.setAllowedMethods(List.of("OPTIONS", "GET"));
        csrfConfig.setAllowedHeaders(List.of("*"));
        csrfConfig.setAllowCredentials(true);
        csrfConfig.setMaxAge(60L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/csrf", csrfConfig);
        source.registerCorsConfiguration("/**", defaultConfig);
        return source;
    }
}