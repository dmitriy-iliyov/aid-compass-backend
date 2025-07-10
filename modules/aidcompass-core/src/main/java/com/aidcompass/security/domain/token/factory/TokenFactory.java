package com.aidcompass.security.domain.token.factory;

import com.aidcompass.security.domain.BaseUserDetails;
import com.aidcompass.security.domain.token.models.Token;
import com.aidcompass.security.domain.token.models.TokenType;

import java.time.Duration;

public interface TokenFactory {
    <T extends BaseUserDetails> Token generateToken(T userDetails, TokenType type);

    <T extends BaseUserDetails> Token generateToken(T userDetails, TokenType type, Duration ttl);
}
