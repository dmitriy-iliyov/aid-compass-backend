package com.aidcompass.security.auth.services;

import com.aidcompass.security.auth.dto.AuthRequest;
import com.aidcompass.security.cookie.CookieFactory;
import com.aidcompass.security.csrf.CsrfAuthenticationStrategy;
import com.aidcompass.security.domain.authority.models.Authority;
import com.aidcompass.security.domain.user.models.MemberUserDetails;
import com.aidcompass.security.domain.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CookieFactory cookieFactory;
    private final CsrfAuthenticationStrategy csrfAuthenticationStrategy;


    public UserAuthServiceImpl(UserService userService,
                               @Qualifier("userAuthenticationManager") AuthenticationManager authenticationManager,
                               CookieFactory cookieFactory,
                               CsrfAuthenticationStrategy csrfAuthenticationStrategy) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.cookieFactory = cookieFactory;
        this.csrfAuthenticationStrategy = csrfAuthenticationStrategy;
    }

    @Override
    public void login(AuthRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addCookie(cookieFactory.generateAuthCookie(authentication));

        List<String> authorityList = ((MemberUserDetails) authentication.getPrincipal())
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        response.addCookie(cookieFactory.generateInfoCookie(Authority.valueOf(authorityList.get(0))));
        csrfAuthenticationStrategy.onAuthentication(request, response);
    }

    @Override
    public void changeAuthorityById(UUID id, Authority authority, HttpServletResponse response) {
        MemberUserDetails details = userService.changeAuthorityById(id, authority);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                details, null, details.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addCookie(cookieFactory.generateAuthCookie(authentication));
        response.addCookie(cookieFactory.generateInfoCookie(authority));
    }
}
