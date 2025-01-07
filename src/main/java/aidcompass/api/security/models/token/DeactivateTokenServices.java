package aidcompass.api.security.models.token;

import aidcompass.api.security.models.token.models.Token;
import aidcompass.api.security.models.token.models.TokenEntity;
import aidcompass.api.security.models.token.models.TokenUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(DeactivateTokenServices.class);


    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) throws UsernameNotFoundException {

        logger.info("Get PreAuthenticatedAuthenticationToken with Authenticated={}", preAuthenticatedAuthenticationToken.isAuthenticated());

        if (preAuthenticatedAuthenticationToken.getPrincipal() instanceof Token token) {
            if(!deactivateTokenRepository.existsById(token.getId())){
                logger.info("Generated tokenUserDetails object");
                return TokenUserDetails.build(token);
            }
        }
        return null;
    }

    @Transactional
    public void save(TokenEntity token) {
        deactivateTokenRepository.save(token);
        logger.info("Saved token");
    }


}
