package com.example.exceptions.not_found;

public class UserNotFoundByUsernameException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByUsernameException() {
        super(MESSAGE);
    }
}
