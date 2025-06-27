package com.aidcompass.security.core;

import com.aidcompass.security.core.handlers.jwt_authentication.CookieJwtAuthenticationFailureHandler;
import com.aidcompass.security.core.handlers.jwt_authentication.CookieJwtAuthenticationSuccessHandler;
import com.aidcompass.security.core.models.token.TokenUserDetailsService;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import com.aidcompass.security.xss.XssFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@RequiredArgsConstructor
public class CookieJwtAuthenticationFilterConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity>{

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
        cookieAuthenticationFilter.setFailureHandler(new CookieJwtAuthenticationFailureHandler());

        http.addFilterAfter(cookieAuthenticationFilter, XssFilter.class);
    }
}
