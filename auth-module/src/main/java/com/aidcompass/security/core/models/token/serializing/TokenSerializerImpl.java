package com.aidcompass.security.core.models.token.serializing;

import com.aidcompass.security.core.models.token.models.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.util.Date;


@RequiredArgsConstructor
public class TokenSerializerImpl implements TokenSerializer {

    private final String SECRET;

    @Override
    public String serialize(Token token) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Header header = Jwts.header()
                .add("alg", key.getAlgorithm())
                .add("typ", "JWT"
                ).build();

        Claims claims = Jwts.claims()
                .id(token.getId().toString())
                .subject(String.valueOf(token.getSubjectId()))
                .issuedAt(Date.from(token.getIssuedAt()))
                .expiration(Date.from(token.getExpiresAt()))
                .add("authorities", token.getAuthorities())
                .build();

        return Jwts.builder()
                .header().add(header).and()
                .claims(claims)
                .signWith(key)
                .compact();
    }
}
