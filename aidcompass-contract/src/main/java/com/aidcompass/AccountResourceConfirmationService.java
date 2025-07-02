package com.aidcompass;

public interface AccountResourceConfirmationService {
    void sendConfirmationMessage(String resource) throws Exception;

    void validateConfirmationToken(String token);
}
