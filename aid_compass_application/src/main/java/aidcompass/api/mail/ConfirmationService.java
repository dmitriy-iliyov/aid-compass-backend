package aidcompass.api.mail;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientMail);

    boolean validateConfirmationAnswer(String input);

}
