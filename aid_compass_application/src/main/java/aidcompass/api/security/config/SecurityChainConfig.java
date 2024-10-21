package aidcompass.api.security.config;


import aidcompass.api.security.core.classes.CookieJwtAuthenticationConfigurer;
import aidcompass.api.security.core.classes.CookieJwtSessionAuthenticationStrategy;
import aidcompass.api.security.core.classes.GettingCsrfTokenFilter;
import aidcompass.api.security.core.classes.handlers.LoginAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityChainConfig {

    private final CookieJwtAuthenticationConfigurer cookieJwtAuthenticationConfigurer;

    private final CookieJwtSessionAuthenticationStrategy cookieJwtSessionAuthenticationStrategy;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .formLogin(formLogin -> formLogin
                        .loginPage("/user/login").permitAll()
                        .usernameParameter("email")
                        .successHandler(new LoginAuthenticationSuccessHandler())
                        .failureForwardUrl("/error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout"))
                .addFilterAfter(new GettingCsrfTokenFilter(), ExceptionTranslationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/home",
                                                 "/user/registration", "/user/login",
                                                 "/admin/**",
                                                 "/error/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(cookieJwtSessionAuthenticationStrategy)
                )
                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy((authentication, request, response) -> {})
                );

        http.apply(cookieJwtAuthenticationConfigurer);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/static/**");
    }

}
