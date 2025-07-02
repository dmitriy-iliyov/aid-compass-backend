package com.aidcompass.security.domain.token.factory;

import com.aidcompass.security.domain.token.models.Token;
import org.springframework.security.core.Authentication;

public interface TokenFactory {

    Token generateToken(Authentication authentication);

}
