package com.aidcompass.security.core.handlers.jwt_authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
public class CookieJwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String FAILURE_AUTHENTICATION_URL;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) {
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        response.setHeader("Location", FAILURE_AUTHENTICATION_URL);
        response.setContentLength(0);
    }
}
