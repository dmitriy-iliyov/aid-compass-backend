package com.aidcompass.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;

public interface CsrfService {
    CsrfToken getMaskedToken(HttpServletRequest request);
}
