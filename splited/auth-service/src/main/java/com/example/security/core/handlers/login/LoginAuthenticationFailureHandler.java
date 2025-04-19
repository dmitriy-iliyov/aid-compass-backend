package com.example.security.core.handlers.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final int MAX_ATTEMPTS = 5;
    private final Map<String, Integer> attemptsCache = new HashMap<>();
    private final String FAILURE_LOGIN_URL;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String username = request.getParameter("username");
        attemptsCache.put(username, attemptsCache.getOrDefault(username, 0) + 1);

        if (attemptsCache.get(username) >= MAX_ATTEMPTS) {
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many failed login attempts. Try again later.");
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Location", FAILURE_LOGIN_URL);
        response.setContentLength(0);
    }
}
