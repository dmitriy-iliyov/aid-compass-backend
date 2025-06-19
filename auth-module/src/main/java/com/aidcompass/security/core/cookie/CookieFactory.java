package com.aidcompass.security.core.cookie;

import com.aidcompass.enums.Authority;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;

public interface CookieFactory {
    Cookie generateAuthCookie(Authentication authentication);

    Cookie generateInfoCookie(Authority authority);
}
