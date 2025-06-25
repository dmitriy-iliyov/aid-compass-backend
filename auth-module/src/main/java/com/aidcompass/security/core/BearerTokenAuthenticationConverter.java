//package com.aidcompass.security.core;
//
//import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationConverter;
//
//@RequiredArgsConstructor
//public class BearerTokenAuthenticationConverter implements AuthenticationConverter {
//
//    private final TokenDeserializer tokenDeserializer;
//
//
//    @Override
//    public Authentication convert(HttpServletRequest request) {
//        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
//
//        }
//        return null;
//    }
//}
