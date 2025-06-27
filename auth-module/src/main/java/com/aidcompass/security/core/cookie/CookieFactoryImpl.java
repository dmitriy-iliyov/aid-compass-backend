package com.aidcompass.security.core.cookie;

import com.aidcompass.enums.Authority;
import com.aidcompass.security.core.models.token.factory.TokenFactory;
import com.aidcompass.security.core.models.token.models.Token;
import com.aidcompass.security.core.models.token.serializing.TokenSerializer;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class CookieFactoryImpl implements CookieFactory {

    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public CookieFactoryImpl(@Qualifier("userTokenFactory") TokenFactory tokenFactory,
                             TokenSerializer tokenSerializer
    ) {
        this.tokenFactory = tokenFactory;
        this.tokenSerializer = tokenSerializer;
    }

    @Override
    public Cookie generateAuthCookie(Authentication authentication) {
        Token token = tokenFactory.generateToken(authentication);
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
