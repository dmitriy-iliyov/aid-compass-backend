package com.example.rest_clients;


public interface UserService {

    void confirmByEmail(String email);

    void recoverPassword(String email, String password);
}
