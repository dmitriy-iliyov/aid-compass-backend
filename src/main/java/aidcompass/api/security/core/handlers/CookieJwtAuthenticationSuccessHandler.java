//package aidcompass.api.security.core.classes.handlers;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import java.io.IOException;
//
//public class CookieJwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final Logger logger = LoggerFactory.getLogger(CookieJwtAuthenticationSuccessHandler.class);
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//
//        String targetUrl = "/user/profile";
//        String failureUrl = "/user/login";
//        String currentUrl = request.getRequestURI();
//
//        logger.info("Request URI: {}", currentUrl);
//
//        logger.info("Get PreAuthenticatedAuthenticationToken with Authenticated={}", authentication.isAuthenticated());
//
//        if(authentication.isAuthenticated()){
//            if(currentUrl.equals("/home")){
//                logger.info("Authentication success redirecting from {} to {}", currentUrl, targetUrl);
//                response.sendRedirect(targetUrl);
//            } else {
//                logger.info("Redirecting from {} to {} forbidden", currentUrl, targetUrl);
//                response.setStatus(HttpServletResponse.SC_OK);
//            }
//        } else {
//            logger.info("Authentication failed for request {}, redirecting to {}", currentUrl, failureUrl);
//            response.sendRedirect(failureUrl);
//        }
//    }
//}
