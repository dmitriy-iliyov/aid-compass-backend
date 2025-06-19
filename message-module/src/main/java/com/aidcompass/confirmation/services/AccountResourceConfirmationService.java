package com.aidcompass.confirmation.services;

public interface AccountResourceConfirmationService {
    void sendConfirmationMessage(String resource) throws Exception;

    void validateConfirmationToken(String token);
}
