package com.aidcompass.recovery;


import jakarta.mail.MessagingException;

public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource) throws Exception;

    void recoverPassword(String token, String password);
}
