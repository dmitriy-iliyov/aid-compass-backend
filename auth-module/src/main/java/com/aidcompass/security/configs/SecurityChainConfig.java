package com.aidcompass.security.configs;


import com.aidcompass.security.core.CookieJwtAuthenticationFilterConfigurer;
import com.aidcompass.security.core.handlers.logout.DeactivatingJwtLogoutHandler;
import com.aidcompass.security.core.handlers.logout.LogoutSuccessHandlerImpl;
import com.aidcompass.security.core.models.token.DeactivateTokenServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityChainConfig {

    private final CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationConfigurer;
    private final CsrfTokenRepository csrfTokenRepository;
    private final DeactivateTokenServices deactivateTokenServices;


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .logout(logout -> logout
//                        .logoutUrl(LOGOUT_URL)
//                        .addLogoutHandler(new CookieClearingLogoutHandler("__Host-auth_token", "XSRF-TOKEN"))
//                        .addLogoutHandler(new DeactivatingJwtLogoutHandler(deactivateTokenServices))
//                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
//                )
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
//                            response.setHeader("Location", USER_LOGIN_PAGE_URL);
//                            response.setContentLength(0);
//                        })
//                )
//                .addFilterAfter(new XssFilter(), CsrfFilter.class)
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers(NO_AUTH_PERMITTED_URL_LIST).permitAll()
//                        .requestMatchers(HttpMethod.POST, USER_REGISTRATION_URL).permitAll()
//                        .anyRequest().authenticated()
//                )

//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/v1/contacts/public/**").permitAll()
                        .requestMatchers("/api/v1/contacts/secondary/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/days/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/intervals/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/avatars/**").permitAll()
                        .anyRequest().permitAll())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new CookieClearingLogoutHandler("__Host-auth_token", "XSRF-TOKEN", "auth_info"))
                        .addLogoutHandler(new DeactivatingJwtLogoutHandler(deactivateTokenServices))
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
//                            response.setHeader("Location", USER_LOGIN_PAGE_URL);
//                            response.setContentLength(0);
//                        })
//                )
//                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository)
//                        .ignoringRequestMatchers("/api/auth/login", "/api/v1/contact", "/csrf")
//                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
//                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
//                )
//                .addFilterAfter(new XssFilter(), CsrfFilter.class)
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
