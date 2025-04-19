package com.example.confirmation;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientResource);

    void validateConfirmationToken(String inputToken);

}
