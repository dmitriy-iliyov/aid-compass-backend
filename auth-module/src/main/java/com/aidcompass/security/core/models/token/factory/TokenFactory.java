package com.aidcompass.security.core.models.token.factory;

import com.aidcompass.security.core.models.token.models.Token;
import org.springframework.security.core.Authentication;

public interface TokenFactory {

    Token generateToken(Authentication authentication);

}
