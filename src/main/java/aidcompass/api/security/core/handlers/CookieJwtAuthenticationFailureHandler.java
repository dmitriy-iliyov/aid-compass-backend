//package aidcompass.api.security.core.classes.handlers;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//
//public class CookieJwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        AuthenticationException exception) throws IOException, ServletException {
//
//        String failureUrl = "/user/login";
//        String requestUri = request.getRequestURI();
//        response.sendRedirect(failureUrl);
//    }
//
//}
