package com.aidcompass.security.configs;


import com.aidcompass.security.core.CookieJwtAuthenticationFilterConfigurer;
import com.aidcompass.security.core.handlers.CsrfAccessDeniedHandler;
import com.aidcompass.security.core.handlers.DefaultAuthenticationEntryPoint;
import com.aidcompass.security.core.handlers.logout.DeactivatingJwtLogoutHandler;
import com.aidcompass.security.core.handlers.logout.LogoutSuccessHandlerImpl;
import com.aidcompass.security.core.models.token.DeactivateTokenServices;
import com.aidcompass.security.xss.XssFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityChainConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final CsrfTokenRepository csrfTokenRepository;
    private final CsrfAccessDeniedHandler csrfAccessDeniedHandler;
    private final CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationConfigurer;
    private final DeactivateTokenServices deactivateTokenServices;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers("/api/auth/login", "/api/v1/contact", "/csrf")
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new CookieClearingLogoutHandler("__Host-auth_token", "XSRF-TOKEN", "auth_info"))
                        .addLogoutHandler(new DeactivatingJwtLogoutHandler(deactivateTokenServices))
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/logout", "/api/auth/me").authenticated()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/confirmations/resource", "/api/confirmations/request").authenticated()
                        .requestMatchers("/api/info/**").permitAll()
                        .requestMatchers("/api/aggregator/doctors/me", "/api/aggregator/doctors/me/**",
                                         "/api/aggregator/jurists/me", "/api/aggregator/jurists/me/**",
                                         "/api/aggregator/customers/**").authenticated()
                        .requestMatchers("/api/aggregator/**").permitAll()
                        .requestMatchers("/api/v1/appointments/**", "/api/v1/appointments/duration/me",
                                         "/api/v1/days/me", "/api/v1/timetable/me/**").authenticated()
                        .requestMatchers("/api/v1/appointments/duration/**", "/api/v1/intervals/nearest/**",
                                         "/api/v1/days/**", "/api/v1/timetable/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/avatars/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(csrfAccessDeniedHandler)
                )
                .headers(headers ->
                        headers.xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        ).contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self'")
                        )
                );

        http.apply(cookieJwtAuthenticationConfigurer);

        return http.build();
    }
}
