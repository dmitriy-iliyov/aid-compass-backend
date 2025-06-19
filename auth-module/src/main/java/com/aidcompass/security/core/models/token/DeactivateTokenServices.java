package com.aidcompass.security.core.models.token;

import com.aidcompass.exceptions.illegal_input.CookieJwtExpired;
import com.aidcompass.exceptions.illegal_input.InvalidPrincipalPassed;
import com.aidcompass.security.core.models.token.models.Token;
import com.aidcompass.security.core.models.token.models.TokenEntity;
import com.aidcompass.security.core.models.token.models.TokenUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeactivateTokenServices implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final DeactivateTokenRepository deactivateTokenRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken)
            throws UsernameNotFoundException {
        if (preAuthenticatedAuthenticationToken.getPrincipal() instanceof Token token) {
            if(!deactivateTokenRepository.existsById(token.getId())){
                return TokenUserDetails.build(token);
            } else {
                throw new CookieJwtExpired();
            }
        }
        throw new InvalidPrincipalPassed();
    }

    @Transactional
    public void save(TokenEntity token) {
        deactivateTokenRepository.save(token);
    }
}
