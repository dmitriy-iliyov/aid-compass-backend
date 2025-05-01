package com.aidcompass.message.clients;


public interface AuthService {

    void confirmByEmail(String email);

    void recoverPassword(String email, String password);
}
