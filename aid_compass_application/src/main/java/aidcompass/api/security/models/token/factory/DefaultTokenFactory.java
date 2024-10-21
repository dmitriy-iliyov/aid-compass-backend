package aidcompass.api.security.models.token.factory;

import aidcompass.api.security.core.interfaces.TokenFactory;
import aidcompass.api.security.models.token.models.Token;
import aidcompass.api.security.models.security_user.models.SecurityUserDetails;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class DefaultTokenFactory implements TokenFactory {

    private final Duration tokenTtl = Duration.ofDays(1);

    private final Logger logger = LoggerFactory.getLogger(DefaultTokenFactory.class);


    @Override
    public Token generateToken(Authentication authentication) {

        logger.info("Generating new token");

        UserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();

        List<String> authorities =
                userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenTtl);

        logger.info("Generating new token ended");

        return new Token(UUID.randomUUID(), userDetails.getUsername(), authorities, issuedAt, expiresAt);
    }
}
