package com.example.exceptions.not_found;

public class UserNotFoundByNumberException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByNumberException() {
        super(MESSAGE);
    }
}