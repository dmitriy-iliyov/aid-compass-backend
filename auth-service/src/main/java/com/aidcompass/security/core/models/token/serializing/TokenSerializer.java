package com.example.security.core.models.token.serializing;

import com.example.clicker.security.core.models.token.models.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
