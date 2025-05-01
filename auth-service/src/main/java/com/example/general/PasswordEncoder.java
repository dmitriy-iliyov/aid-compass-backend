package com.example.general;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean matches(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

}

