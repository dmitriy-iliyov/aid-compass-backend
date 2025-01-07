package aidcompass.api.security.core.classes;

import aidcompass.api.security.models.token.models.Token;
import aidcompass.api.security.core.interfaces.TokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class CookieJwtToTokenAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer tokenDeserializer;

    private final Logger logger = LoggerFactory.getLogger(CookieJwtToTokenAuthenticationConverter.class);


    @Override
    public Authentication convert(HttpServletRequest request) {

        logger.info("Request URI: {}", request.getRequestURI());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String message = authentication != null ? "Authenticated = " + authentication.isAuthenticated() : "Authentication : " + null;
        logger.info(message);

        if(request.getCookies() != null) {

            logger.info("Get cookie from request");

            return Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("__Host-auth-token"))
                    .findFirst()
                    .map(cookie -> {
                        Token token = tokenDeserializer.deserialize(cookie.getValue());
                        logger.info("Deserialized Jwt from cookie to Token");
                        return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
                    }).orElse(null);
        }
        return null;
    }

}
