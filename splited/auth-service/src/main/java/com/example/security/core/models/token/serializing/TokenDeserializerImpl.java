package com.example.security.core.models.token.serializing;

import com.example.clicker.general.exceptions.models.CookieJwtExpired;
import com.example.clicker.security.core.models.token.models.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Log4j2
@Data
@RequiredArgsConstructor
public class TokenDeserializerImpl implements TokenDeserializer {

    private final String SECRET;
    private final Logger logger = LoggerFactory.getLogger(TokenDeserializerImpl.class);

    @Override
    public Token deserialize(String inputJwt) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Jwt<?, ?> jwt;
        try {
             jwt = Jwts.parser()
                     .verifyWith(key)
                     .build()
                     .parseSignedClaims(inputJwt);
            try{
                Claims claims = (Claims) jwt.getPayload();
                UUID id = UUID.fromString(claims.getId());
                UUID subjectId = UUID.fromString(claims.getSubject());
                List<String> authorities = claims.get("authorities", List.class);
                Instant issuedAt = claims.getIssuedAt().toInstant();
                Instant expiresAt = claims.getExpiration().toInstant();

                if (expiresAt != null) {
                    if (Instant.now().isBefore(expiresAt)) {
                        return new Token(id, subjectId, authorities, issuedAt, expiresAt);
                    }
                    throw new CookieJwtExpired("Session expired");
                }

            }catch (Exception e){
                logger.error("Exception when get claims and deserialize to Token: {}", e.getMessage());
            }
        }catch (Exception e){
            logger.error("Exception when check jwt signature: {}", e.getMessage());
        }
        return null;
    }
}
