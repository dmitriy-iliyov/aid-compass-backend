package com.aidcompass.security.core.models.token.serializing;


import com.aidcompass.security.core.models.token.models.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
