package com.aidcompass.security.domain.token.serializing;


import com.aidcompass.security.domain.token.models.Token;

public interface TokenDeserializer {

    Token deserialize(String token);

}
