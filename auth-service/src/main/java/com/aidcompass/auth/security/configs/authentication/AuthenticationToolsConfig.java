package com.example.security.configs.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Log4j2
@RequiredArgsConstructor
public class AuthenticationToolsConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final DeactivateTokenServices deactivateTokenServices;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;
    private final TokenDeserializer tokenDeserializer;


    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        log.info("Successfully configured ProviderManager");
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public CookieJwtSessionAuthenticationStrategy cookieJwtSessionAuthenticationStrategy(){
        return new CookieJwtSessionAuthenticationStrategy(tokenFactory, tokenSerializer);
    }

    @Bean
    public CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationFilterConfigurer(){
        return new CookieJwtAuthenticationFilterConfigurer(deactivateTokenServices, tokenDeserializer);
    }
}
