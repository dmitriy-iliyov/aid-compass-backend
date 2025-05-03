package com.aidcompass.client;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientResource);

    void validateConfirmationToken(String inputToken);

}
