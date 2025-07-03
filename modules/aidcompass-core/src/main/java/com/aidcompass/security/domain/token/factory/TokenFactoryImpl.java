package com.aidcompass.security.domain.token.factory;

import com.aidcompass.security.domain.BaseUserDetails;
import com.aidcompass.security.domain.token.models.Token;
import com.aidcompass.security.domain.token.models.TokenType;
import com.aidcompass.general.utils.uuid.UuidFactory;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TokenFactoryImpl implements TokenFactory {

    private final Duration defaultTtl = Duration.ofDays(1);


    @Override
    public <T extends BaseUserDetails> Token generateToken(T userDetails, TokenType type) {
        return generateToken(userDetails, type, defaultTtl);
    }

    @Override
    public <T extends BaseUserDetails> Token generateToken(T userDetails, TokenType type, Duration ttl) {
        UUID tokenId = UuidFactory.generate();

        UUID serviceId = userDetails.getId();

        List<String> authorities = userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(ttl);

        return new Token(tokenId, serviceId, type, authorities, issuedAt, expiresAt);
    }
}