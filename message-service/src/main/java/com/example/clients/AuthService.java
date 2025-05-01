package com.example.clients;


public interface AuthService {

    void confirmByEmail(String email);

    void recoverPassword(String email, String password);
}
