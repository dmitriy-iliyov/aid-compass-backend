//package aidcompass.api.security.core.classes;
//
//import aidcompass.api.security.models.token.models.Token;
//import aidcompass.api.security.core.interfaces.TokenFactory;
//import aidcompass.api.security.core.interfaces.TokenSerializer;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.session.SessionAuthenticationException;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//
//
//@Data
//@RequiredArgsConstructor
//public class CookieJwtSessionAuthenticationStrategy implements SessionAuthenticationStrategy {
//
//    private final TokenFactory tokenFactory;
//
//    private final TokenSerializer tokenSerializer;
//
//    private final Logger logger = LoggerFactory.getLogger(CookieJwtSessionAuthenticationStrategy.class);
//
//
//    @Override
//    public void onAuthentication(Authentication authentication,
//                                 HttpServletRequest request,
//                                 HttpServletResponse response) throws SessionAuthenticationException {
//
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//
//            logger.info("Request URI: {}", request.getRequestURI());
//
//            Token token = tokenFactory.generateToken(authentication);
//            String jwt = tokenSerializer.serialize(token);
//
//            logger.info("Jwt successfully generated");
//
//            Cookie cookie = new Cookie("__Host-auth-token", jwt);
//            cookie.setPath("/");
//            cookie.setDomain(null);
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.getExpiresAt()));
//
//            logger.info("Cookie successfully added");
//
//            response.addCookie(cookie);
//        }
//    }
//}
