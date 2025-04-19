package com.example.exceptions.user;

public class UserNotFoundByIdException extends UserNotFoundException {

    public UserNotFoundByIdException(String message) {
        super(message);
    }
}
