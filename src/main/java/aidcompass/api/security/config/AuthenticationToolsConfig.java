package aidcompass.api.security.config;

import aidcompass.api.security.models.security_user.SecurityUserService;
import aidcompass.api.security.core.classes.CookieJwtAuthenticationConfigurer;
import aidcompass.api.security.core.classes.CookieJwtSessionAuthenticationStrategy;
import aidcompass.api.security.core.classes.handlers.CookieJwtAuthenticationFailureHandler;
import aidcompass.api.security.core.interfaces.TokenDeserializer;
import aidcompass.api.security.core.interfaces.TokenFactory;
import aidcompass.api.security.core.interfaces.TokenSerializer;
import aidcompass.api.security.models.token.DeactivateTokenServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@RequiredArgsConstructor
public class AuthenticationToolsConfig{

    private final SecurityUserService userService;

    private final PasswordEncoder passwordEncoder;

    private final DeactivateTokenServices deactivateTokenServices;

    private final TokenFactory tokenFactory;

    private final TokenSerializer tokenSerializer;

    private final TokenDeserializer tokenDeserializer;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationToolsConfig.class);


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        logger.info("Successfully configured AuthenticationManager");
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public CookieJwtAuthenticationConfigurer cookieJwtAuthenticationConfigurer() {
        return new CookieJwtAuthenticationConfigurer(deactivateTokenServices, tokenDeserializer);
    }

    @Bean
    public CookieJwtSessionAuthenticationStrategy cookieJwtSessionAuthenticationStrategy(){
        return new CookieJwtSessionAuthenticationStrategy(tokenFactory, tokenSerializer);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CookieJwtAuthenticationFailureHandler();
    }

}
