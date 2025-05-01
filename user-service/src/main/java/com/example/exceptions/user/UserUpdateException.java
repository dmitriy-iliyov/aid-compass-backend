package com.example.exceptions.user;

import com.aidcompass.common.global_exceptions.Exception;

public class UserUpdateException extends Exception {

    private String message;

    public UserUpdateException(String message) {
        this.message = message;
    }

}
