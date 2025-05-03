package com.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@RequiredArgsConstructor
public class JwtNCsrfSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final CookieJwtSessionAuthenticationStrategy cookieJwtSessionAuthenticationStrategy;
    private final CsrfTokenSessionAuthenticationStrategy csrfTokenAuthenticationStrategy;


    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        cookieJwtSessionAuthenticationStrategy.onAuthentication(authentication, request, response);
        csrfTokenAuthenticationStrategy.onAuthentication(authentication, request, response);
    }
}
