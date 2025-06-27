package com.aidcompass.security.core;

import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@RequiredArgsConstructor
public class BearerTokenAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer tokenDeserializer;


    @Override
    public Authentication convert(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            String [] splitBearerToken = bearerToken.split(" ");
            if (splitBearerToken[0].equals("Bearer")) {
                String jwt = splitBearerToken[1];
                if (jwt != null) {
                    return new PreAuthenticatedAuthenticationToken(tokenDeserializer.deserialize(jwt), "Bearer");
                }
                throw new BadCredentialsException("Bearer token is null!");
            }
            throw new BadCredentialsException("Bearer part is missing!");
        }
        throw new BadCredentialsException("Authorization header is missing!");
    }
}
