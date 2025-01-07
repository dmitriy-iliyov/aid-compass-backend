package aidcompass.api.confirmation_service;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientMail);

    boolean validateConfirmationAnswer(String input);

}
