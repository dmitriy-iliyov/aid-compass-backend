package com.aidcompass.security.auth.services;

import com.aidcompass.security.auth.dto.ServiceAuthRequest;
import com.aidcompass.security.domain.authority.models.Authority;
import com.aidcompass.security.domain.service.ServiceUserDetails;
import com.aidcompass.security.domain.service.ServiceUserDetailsService;
import com.aidcompass.security.domain.token.factory.TokenFactory;
import com.aidcompass.security.domain.token.models.Token;
import com.aidcompass.security.domain.token.models.TokenType;
import com.aidcompass.security.domain.token.serializing.TokenSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class SystemAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public SystemAuthService(@Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                             @Qualifier("serviceUserDetailsService") UserDetailsService userDetailsService,
                             TokenFactory tokenFactory,
                             TokenSerializer tokenSerializer) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenFactory = tokenFactory;
        this.tokenSerializer = tokenSerializer;
    }

    public Map<String, String> login(ServiceAuthRequest requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.name(), requestDto.key())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token = tokenFactory.generateToken(
                (ServiceUserDetails) authentication.getPrincipal(),
                TokenType.SERVICE,
                Duration.ofDays(2)
        );
        return Map.of("token", tokenSerializer.serialize(token));
    }

    public Map<String, String> generateToken(String serviceName, Integer dtl) {
        ServiceUserDetails userDetails = (ServiceUserDetails) userDetailsService.loadUserByUsername(serviceName);
        Token token = tokenFactory.generateToken(userDetails, TokenType.SERVICE, Duration.ofDays(dtl));
        return Map.of("token", tokenSerializer.serialize(token));
    }
}
