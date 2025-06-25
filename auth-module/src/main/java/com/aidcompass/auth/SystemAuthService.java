package com.aidcompass;

import com.aidcompass.base_dto.security.SystemAuthRequest;
import com.aidcompass.security.core.models.token.factory.TokenFactory;
import com.aidcompass.security.core.models.token.models.Token;
import com.aidcompass.security.core.models.token.serializing.TokenSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public String login(SystemAuthRequest requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                //??
                new UsernamePasswordAuthenticationToken(requestDto.serviceName(), requestDto.password())
        );
        Token token = tokenFactory.generateToken(authentication);
        return tokenSerializer.serialize(token);
    }
}
