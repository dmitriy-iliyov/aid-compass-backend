package com.example.recovery;


public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource);

    void recoverPassword(String token, String password);
}
