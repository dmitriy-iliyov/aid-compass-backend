package com.aidcompass;

import com.aidcompass.base_dto.AuthRequest;
import com.aidcompass.contracts.AuthService;
import com.aidcompass.enums.Authority;
import com.aidcompass.security.core.cookie.CookieFactory;
import com.aidcompass.security.csrf.CsrfAuthenticationStrategy;
import com.aidcompass.user.models.DefaultUserDetails;
import com.aidcompass.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CookieFactory cookieFactory;
    private final CsrfAuthenticationStrategy csrfAuthenticationStrategy;


    @Override
    public void login(AuthRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addCookie(cookieFactory.generateAuthCookie(authentication));

        List<String> authorityList = ((DefaultUserDetails) authentication.getPrincipal())
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        response.addCookie(cookieFactory.generateInfoCookie(Authority.valueOf(authorityList.get(0))));
        //csrfAuthenticationStrategy.onAuthentication(authentication, request, response);
    }

    @Override
    public void changeAuthorityById(UUID id, Authority authority, HttpServletResponse response) {
        DefaultUserDetails details = userService.changeAuthorityById(id, authority);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                details, null, details.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addCookie(cookieFactory.generateAuthCookie(authentication));
        response.addCookie(cookieFactory.generateInfoCookie(authority));
    }
}
