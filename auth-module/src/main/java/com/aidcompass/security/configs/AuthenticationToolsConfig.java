package com.aidcompass.security.configs;

import com.aidcompass.security.core.CookieJwtAuthenticationFilterConfigurer;
import com.aidcompass.security.core.handlers.DefaultAuthenticationEntryPoint;
import com.aidcompass.security.core.models.token.DeactivateTokenServices;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Log4j2
@RequiredArgsConstructor
public class AuthenticationToolsConfig {

    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;
    private final DeactivateTokenServices deactivateTokenServices;
    private final TokenDeserializer tokenDeserializer;
    private final ObjectMapper mapper;


    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
        log.info("Successfully configured ProviderManager with DaoAuthenticationProvider");
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationFilterConfigurer(){
        return new CookieJwtAuthenticationFilterConfigurer(deactivateTokenServices, tokenDeserializer);
    }

    @Bean
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint(mapper);
    }
}
