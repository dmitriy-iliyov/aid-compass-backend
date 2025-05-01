package com.example.security.core.models.token.factory;

import com.example.clicker.security.core.models.token.models.Token;
import org.springframework.security.core.Authentication;

public interface TokenFactory {

    Token generateToken(Authentication authentication);

}
