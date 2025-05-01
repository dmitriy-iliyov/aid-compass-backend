package com.aidcompass.message.recovery;


import jakarta.mail.MessagingException;

public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource) throws MessagingException;

    void recoverPassword(String token, String password);
}
