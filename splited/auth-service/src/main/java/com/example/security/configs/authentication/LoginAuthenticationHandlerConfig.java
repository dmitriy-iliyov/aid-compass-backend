package com.example.security.configs.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class LoginAuthenticationHandlerConfig {

    private static final String CLICKER_URL = "/clicker";
    private static final String ADMIN_HOME_PAGE_URL = "/admins/home";
    private static final String PROFILE_URL = "/users/user/profile";
    private final CookieJwtSessionAuthenticationStrategy cookieJwtAuthenticationStrategy;
    private final CsrfTokenSessionAuthenticationStrategy csrfAuthenticationStrategy;

    @Bean
    public LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler(){
        LoginAuthenticationSuccessHandler loginSuccessHandler =
                new LoginAuthenticationSuccessHandler(
                        new JwtNCsrfSessionAuthenticationStrategy(
                                cookieJwtAuthenticationStrategy,
                                csrfAuthenticationStrategy), PROFILE_URL, ADMIN_HOME_PAGE_URL);
        loginSuccessHandler.setDefaultTargetUrl(CLICKER_URL);
        loginSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return loginSuccessHandler;
    }
}
