package com.example.security.configs.csrf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CsrfToolsConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .path("/")
                .domain(null)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build());
        return csrfTokenRepository;
    }

    @Bean
    public CsrfTokenSessionAuthenticationStrategy csrfTokenAuthenticationStrategy(CsrfTokenRepository csrfTokenRepository){
        return new CsrfTokenSessionAuthenticationStrategy(csrfTokenRepository);
    }

    @Bean
    public CsrfTokenMasker csrfTokenMasker() {
        return new CsrfTokenMaskerImpl();
    }
}
