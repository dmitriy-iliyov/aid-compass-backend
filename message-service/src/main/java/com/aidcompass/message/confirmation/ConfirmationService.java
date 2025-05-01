package com.aidcompass.message.confirmation;

import jakarta.mail.MessagingException;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientResource) throws MessagingException;

    void validateConfirmationToken(String inputToken);

}
