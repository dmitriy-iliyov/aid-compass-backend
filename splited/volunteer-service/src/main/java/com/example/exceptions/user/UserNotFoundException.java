package com.example.exceptions.user;

import com.example.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
