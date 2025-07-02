package com.aidcompass.security.domain.token.factory;

import com.aidcompass.security.domain.token.models.Token;
import com.aidcompass.security.domain.token.models.TokenType;
import com.aidcompass.security.domain.service.ServiceUserDetails;
import com.aidcompass.general.utils.uuid.UuidFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ServiceTokenFactory implements TokenFactory {

    private final Duration tokenTtl = Duration.ofDays(1);


    @Override
    public Token generateToken(Authentication authentication) {
        UUID tokenId = UuidFactory.generate();

        ServiceUserDetails serviceUserDetails = (ServiceUserDetails) authentication.getPrincipal();
        UUID serviceId = serviceUserDetails.getId();

        List<String> authorities = serviceUserDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenTtl);

        return new Token(tokenId, serviceId, TokenType.SERVICE, authorities, issuedAt, expiresAt);
    }
}