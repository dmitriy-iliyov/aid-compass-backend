package com.aidcompass.security.cookie;

import com.aidcompass.security.domain.authority.models.Authority;
import com.aidcompass.security.domain.token.factory.TokenFactory;
import com.aidcompass.security.domain.token.models.Token;
import com.aidcompass.security.domain.token.models.TokenType;
import com.aidcompass.security.domain.token.serializing.TokenSerializer;
import com.aidcompass.security.domain.user.models.MemberUserDetails;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class CookieFactoryImpl implements CookieFactory {

    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public CookieFactoryImpl(TokenFactory tokenFactory,
                             TokenSerializer tokenSerializer
    ) {
        this.tokenFactory = tokenFactory;
        this.tokenSerializer = tokenSerializer;
    }

    @Override
    public Cookie generateAuthCookie(Authentication authentication) {
        Token token = tokenFactory.generateToken(
                (MemberUserDetails) authentication.getPrincipal(),
                TokenType.USER
        );
        String jwt = tokenSerializer.serialize(token);

        Cookie cookie = new Cookie("__Host-auth_token", jwt);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.getExpiresAt()));

        return cookie;
    }

    @Override
    public Cookie generateInfoCookie(Authority authority) {
        Cookie cookie = new Cookie("auth_info", authority.getAuthority());
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge(Integer.MAX_VALUE);
        return cookie;
    }
}
