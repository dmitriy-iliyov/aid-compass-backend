package com.example.exceptions.user;

import com.example.global_exceptions.BaseNotFoundException;

public class UserBaseNotFoundException extends BaseNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserBaseNotFoundException() {
        super(MESSAGE);
    }

    public UserBaseNotFoundException(String message) {
        super(message);
    }
}
