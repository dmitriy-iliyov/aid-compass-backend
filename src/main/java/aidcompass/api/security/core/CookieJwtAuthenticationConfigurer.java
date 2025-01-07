package aidcompass.api.security.core.classes;

import aidcompass.api.security.models.token.models.TokenEntity;
import aidcompass.api.security.models.token.models.TokenUserDetails;
import aidcompass.api.security.core.classes.handlers.CookieJwtAuthenticationFailureHandler;
import aidcompass.api.security.core.classes.handlers.CookieJwtAuthenticationSuccessHandler;
import aidcompass.api.security.core.interfaces.TokenDeserializer;
import aidcompass.api.security.models.token.DeactivateTokenServices;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;

import java.io.IOException;
import java.time.Instant;

@Data
@RequiredArgsConstructor
public class CookieJwtAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity>{

    private final DeactivateTokenServices deactivateTokenServices;

    private final TokenDeserializer tokenDeserializer;

    private final Logger logger = LoggerFactory.getLogger(CookieJwtAuthenticationConfigurer.class);


    @Override
    public void init(HttpSecurity http) throws Exception {

        http.logout(logout -> logout.addLogoutHandler(new CookieClearingLogoutHandler("__Host-auth-token"))
                .addLogoutHandler((request, response, authentication) -> {

                    logger.info("Authentication {}", authentication);

                    if(authentication != null && authentication.getPrincipal() instanceof TokenUserDetails tokenUserDetails) {
                        deactivateTokenServices.save(new TokenEntity(tokenUserDetails.getToken().getId(), Instant.now()));
                    }

                    try {
                        response.sendRedirect("/home");
                    } catch (IOException e) {
                        logger.error("init() : Something wrong when send redirect {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter cookieAuthenticationFilter = new AuthenticationFilter(
                http.getSharedObject(AuthenticationManager.class),
                new CookieJwtToTokenAuthenticationConverter(tokenDeserializer)
        );

        cookieAuthenticationFilter.setSuccessHandler(new CookieJwtAuthenticationSuccessHandler());
//        cookieAuthenticationFilter.setSuccessHandler((request, response, authentication) -> {});
        cookieAuthenticationFilter.setFailureHandler(new CookieJwtAuthenticationFailureHandler());

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(deactivateTokenServices);

        http.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
                .authenticationProvider(preAuthenticatedAuthenticationProvider);
    }
}
