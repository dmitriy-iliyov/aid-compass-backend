//package com.aidcompass.security.configs;
//
//import com.aidcompass.security.core.CookieJwtAuthenticationFilterConfigurer;
//import com.aidcompass.security.core.handlers.DefaultAuthenticationEntryPoint;
//import com.aidcompass.security.core.models.token.TokenUserDetailsService;
//import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@Configuration
//@Log4j2
//@RequiredArgsConstructor
//public class AuthenticationToolsConfig {
//
//    private final UserDetailsService userService;
//    private final PasswordEncoder passwordEncoder;
//    private final TokenUserDetailsService tokenUserDetailsService;
//    private final TokenDeserializer tokenDeserializer;
//    private final ObjectMapper mapper;
//
//
//    public AuthenticationToolsConfig()
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
//        log.info("Successfully configured ProviderManager with DaoAuthenticationProvider");
//        return new ProviderManager(daoAuthenticationProvider);
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    public CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationFilterConfigurer(){
//        return new CookieJwtAuthenticationFilterConfigurer(tokenUserDetailsService, tokenDeserializer);
//    }
//
//    @Bean
//    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
//        return new DefaultAuthenticationEntryPoint(mapper);
//    }
//}

package com.aidcompass.security.configs;

import com.aidcompass.security.core.CookieJwtAuthenticationFilterConfigurer;
import com.aidcompass.security.core.handlers.DefaultAuthenticationEntryPoint;
import com.aidcompass.security.core.models.token.TokenUserDetailsService;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import com.aidcompass.service.ServiceUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;


@Configuration
@Log4j2
public class AuthenticationToolsConfig {

    private final UserDetailsService userDetailsService;
    private final UserDetailsService serviceUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUserDetailsService tokenUserDetailsService;
    private final TokenDeserializer tokenDeserializer;
    private final ObjectMapper mapper;


    public AuthenticationToolsConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                                     @Qualifier("serviceUserDetailsService") UserDetailsService serviceUserDetailsService,
                                     PasswordEncoder passwordEncoder,
                                     TokenUserDetailsService tokenUserDetailsService,
                                     TokenDeserializer tokenDeserializer,
                                     ObjectMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.serviceUserDetailsService = serviceUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUserDetailsService = tokenUserDetailsService;
        this.tokenDeserializer = tokenDeserializer;
        this.mapper = mapper;
    }

    @Bean(name = "userAuthenticationManager")
    @Primary
    public AuthenticationManager userAuthenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(tokenUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
    }

    @Bean(name = "serviceAuthenticationManager")
    public AuthenticationManager serviceAuthenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(serviceUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationFilterConfigurer(){
        return new CookieJwtAuthenticationFilterConfigurer(tokenUserDetailsService, tokenDeserializer);
    }

    @Bean
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint(mapper);
    }
}
