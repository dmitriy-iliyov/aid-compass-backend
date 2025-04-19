package com.example.security.configs;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityChainConfig {

    private static final String HEALTH_CHECK_URL = "/health";
    private static final String USER_REGISTRATION_URL = "/users";
    private static final String USER_LOGIN_URL = "/login";
    private static final String GET_CSRF_TOKEN_URL = "/csrf";
    private static final String CONFIRMING_URL = "/confirm/**";
    private static final String PASSWORD_RECOVERING_URL = "/password-recovery/**";
    private static final String USER_LOGOUT_URL = "/logout";

    private static final String HOME_PAGE_URL = "/home";
    private static final String USER_REGISTRATION_PAGE_URL = "/users/create";
    private static final String USER_LOGIN_PAGE_URL = "/users/login";

    private static final String [] NO_AUTH_PERMITTED_URL_LIST = {
            HOME_PAGE_URL, USER_LOGIN_URL, USER_REGISTRATION_PAGE_URL, CONFIRMING_URL, PASSWORD_RECOVERING_URL, HEALTH_CHECK_URL,
            "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
            //to del
            "user_pass_recovery_form.html", "/test/**", "/currencies/**", "/users/**", HOME_PAGE_URL + "/**"
    };
    private static final String [] NO_CSRF_PERMITTED_URL_LIST = {
            GET_CSRF_TOKEN_URL, USER_REGISTRATION_URL, USER_REGISTRATION_PAGE_URL, USER_LOGIN_URL, CONFIRMING_URL, PASSWORD_RECOVERING_URL,
            "/test/**",  "/currencies/**", "/users/**"
    };

    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationConfigurer;
    private final CsrfTokenRepository csrfTokenRepository;
    private final DeactivateTokenServices deactivateTokenServices;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl(USER_LOGIN_URL)
                        .loginPage(USER_LOGIN_PAGE_URL)
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginAuthenticationSuccessHandler)
                        .failureHandler(new LoginAuthenticationFailureHandler(USER_LOGIN_PAGE_URL))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(USER_LOGOUT_URL)
                        .addLogoutHandler(new CookieClearingLogoutHandler("__Host-auth_token", "XSRF-TOKEN"))
                        .addLogoutHandler(new DeactivatingJwtLogoutHandler(deactivateTokenServices))
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl(HOME_PAGE_URL))
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                            response.setHeader("Location", USER_LOGIN_PAGE_URL);
                            response.setContentLength(0);
                        })
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
//                .addFilterBefore(new CheckUserConfirmationFilter(), ExceptionTranslationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(NO_AUTH_PERMITTED_URL_LIST).permitAll()
                        .requestMatchers(HttpMethod.POST, USER_REGISTRATION_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers(NO_CSRF_PERMITTED_URL_LIST)
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .headers(headers ->
                        headers.xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        ).contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self'")
                        ));

        http.apply(cookieJwtAuthenticationConfigurer);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/static/**");
    }

}
