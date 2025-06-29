package com.aidcompass.auth;

import com.aidcompass.base_dto.security.ServiceAuthRequest;
import com.aidcompass.security.core.models.token.factory.TokenFactory;
import com.aidcompass.security.core.models.token.models.Token;
import com.aidcompass.security.core.models.token.serializing.TokenSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public SystemAuthService(@Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                             @Qualifier("serviceTokenFactory") TokenFactory tokenFactory,
                             TokenSerializer tokenSerializer
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenFactory = tokenFactory;
        this.tokenSerializer = tokenSerializer;
    }

    public Map<String, String> login(ServiceAuthRequest requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.name(), requestDto.key())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token = tokenFactory.generateToken(authentication);
        return Map.of("token", tokenSerializer.serialize(token));
    }
}
