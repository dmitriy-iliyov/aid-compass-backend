package com.example.security.core.models.token.serializing;

import com.example.clicker.security.core.models.token.models.Token;

public interface TokenDeserializer {

    Token deserialize(String token);

}
