package com.aidcompass.security.core.models.token.factory;

import com.aidcompass.security.core.models.token.models.Token;
import com.aidcompass.user.models.DefaultUserDetails;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class TokenFactoryImpl implements TokenFactory {

    private final Duration tokenTtl = Duration.ofDays(1);


    @Override
    public Token generateToken(Authentication authentication) {
        UUID tokenId = UuidCreator.getTimeOrderedEpoch();

        DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
        UUID userId = defaultUserDetails.getId();

        List<String> authorities =
                defaultUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenTtl);

        return new Token(tokenId, userId, authorities, issuedAt, expiresAt);
    }
}