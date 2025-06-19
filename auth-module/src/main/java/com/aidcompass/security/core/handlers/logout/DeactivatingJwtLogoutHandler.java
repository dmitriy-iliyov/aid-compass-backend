package com.aidcompass.security.core.handlers.logout;

import com.aidcompass.security.core.models.token.DeactivateTokenServices;
import com.aidcompass.security.core.models.token.models.TokenEntity;
import com.aidcompass.security.core.models.token.models.TokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.time.Instant;

@RequiredArgsConstructor
public class DeactivatingJwtLogoutHandler implements LogoutHandler {

    private final DeactivateTokenServices deactivateTokenServices;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication != null && authentication.getPrincipal() instanceof TokenUserDetails tokenUserDetails) {
            deactivateTokenServices.save(new TokenEntity(tokenUserDetails.getToken().getId(), Instant.now()));
        }
    }
}
