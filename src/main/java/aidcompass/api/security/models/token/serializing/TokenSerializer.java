package aidcompass.api.security.core.interfaces;

import aidcompass.api.security.models.token.models.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
