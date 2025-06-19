package com.aidcompass.security.core;

import com.aidcompass.security.core.handlers.jwt_authentication.CookieJwtAuthenticationFailureHandler;
import com.aidcompass.security.core.handlers.jwt_authentication.CookieJwtAuthenticationSuccessHandler;
import com.aidcompass.security.core.models.token.DeactivateTokenServices;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;


@RequiredArgsConstructor
public class CookieJwtAuthenticationFilterConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity>{

    private final static String FAILURE_AUTHENTICATION_URL = "/users/login";
    private final DeactivateTokenServices deactivateTokenServices;
    private final TokenDeserializer tokenDeserializer;

    @Override
    public void init(HttpSecurity builder) {}

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationFilter cookieAuthenticationFilter = new AuthenticationFilter(
                http.getSharedObject(AuthenticationManager.class),
                new CookieJwtAuthenticationConverter(tokenDeserializer)
        );

        cookieAuthenticationFilter.setSuccessHandler(new CookieJwtAuthenticationSuccessHandler());
        cookieAuthenticationFilter.setFailureHandler(new CookieJwtAuthenticationFailureHandler(FAILURE_AUTHENTICATION_URL));

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(deactivateTokenServices);
        http.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
                .authenticationProvider(preAuthenticatedAuthenticationProvider);
    }
}
