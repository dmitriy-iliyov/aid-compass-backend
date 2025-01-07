package aidcompass.api.security.models.token.serializing;

import aidcompass.api.security.models.token.models.Token;
import aidcompass.api.security.core.interfaces.TokenDeserializer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@RequiredArgsConstructor
public class DefaultTokenDeserializer implements TokenDeserializer {

    private final String SECRET;

    private final Logger logger = LoggerFactory.getLogger(DefaultTokenDeserializer.class);

    @Override
    public Token deserialize(String jwt) {

        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        JwtParser jwtParser =
                    Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build();

        try {
            Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
            try{
                UUID id = UUID.fromString(claims.getId());
                String subject = claims.getSubject();
                List<String> authorities = claims.get("authorities", List.class);
                Instant issuedAt = claims.getIssuedAt().toInstant();
                Instant expiresAt = claims.getExpiration().toInstant();

                return new Token(id, subject, authorities, issuedAt, expiresAt);

            }catch (Exception e){
                logger.error("Exception when get claims and make new jwt: {}", e.getMessage());
            }
        }catch (Exception e){
            logger.error("Exception when check jwt signature: {}", e.getMessage());
        }
        return null;
    }
}
