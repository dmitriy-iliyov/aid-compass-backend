package com.aidcompass.security.configs.security_filter_chain;

import com.aidcompass.security.core.BearerJwtAuthenticationConverter;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
public class ServiceSecurityChainConfig {

    private final AuthenticationManager authenticationManager;
    private final TokenDeserializer tokenDeserializer;


    public ServiceSecurityChainConfig(@Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                                      TokenDeserializer tokenDeserializer) {
        this.authenticationManager = authenticationManager;
        this.tokenDeserializer = tokenDeserializer;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain serviceSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationFilter bearerAuthenticationFilter = new AuthenticationFilter(
                authenticationManager,
                new BearerJwtAuthenticationConverter(tokenDeserializer)
        );

        http
                .securityMatcher("/api/system/**")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(bearerAuthenticationFilter, CsrfFilter.class)
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers(new IpAddressMatcher("192.168.0.0/16")).permitAll()
                        .requestMatchers("/api/system/v1/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new BasicAuthenticationEntryPoint()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
