package com.aidcompass.security.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ServiceSecurityChainConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter authenticationFilter;


    public ServiceSecurityChainConfig(@Qualifier("serviceCorsConfigurationSource") CorsConfigurationSource corsConfigurationSource,
                                      @Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                                      @Qualifier("bearerAuthenticationFilter") AuthenticationFilter authenticationFilter) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain serviceSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/system/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(authenticationFilter, CsrfFilter.class)
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return http.build();
    }
}
