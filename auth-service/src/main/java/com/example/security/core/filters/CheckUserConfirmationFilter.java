package com.example.security.core.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


public class CheckUserConfirmationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getAuthorities().stream().anyMatch(
                    auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER.getAuthority()))) {
                if (Arrays.stream(
                        request.getCookies()).noneMatch(cookie -> cookie.getName().equals("__Secure-auth_details"))) {
                    Cookie cookie = new Cookie("__Secure-auth_details", "Unconfirmed");
                    cookie.setPath("/");
                    cookie.setSecure(true);
                    cookie.setAttribute("SameSite", "Strict");
                    response.addCookie(cookie);
                }
            } else if(authentication.getAuthorities().stream().anyMatch(
                    auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER.getAuthority()))) {
                if (Arrays.stream(
                        request.getCookies()).anyMatch(cookie ->
                                cookie.getName().equals("__Secure-auth_details") &&
                                cookie.getValue().equals("Unconfirmed")
                        )
                ) {
                    Cookie cookieToRemove = new Cookie("__Secure-auth_details", "");
                    cookieToRemove.setMaxAge(0);
                    cookieToRemove.setPath("/");
                    response.addCookie(cookieToRemove);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
