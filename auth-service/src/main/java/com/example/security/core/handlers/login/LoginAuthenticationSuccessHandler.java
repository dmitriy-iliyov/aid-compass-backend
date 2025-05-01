package com.example.security.core.handlers.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtNCsrfSessionAuthenticationStrategy authenticationStrategy;
    private final String DEFAULT_UNCONFIRMED_URL;
    private final String DEFAULT_ADMIN_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        authenticationStrategy.onAuthentication(authentication, request, response);
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        response.setHeader("Location", getTargetUrl(authentication));
        response.setContentLength(0);
    }

    private String getTargetUrl(Authentication authentication){
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(Authority.ROLE_ADMIN.toString()))) {
            return DEFAULT_ADMIN_URL;
        } else if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER.toString()))) {
            return DEFAULT_UNCONFIRMED_URL;
        }
        return this.getDefaultTargetUrl();
    }
}