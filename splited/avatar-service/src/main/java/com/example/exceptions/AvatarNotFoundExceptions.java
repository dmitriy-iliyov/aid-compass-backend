package com.example.exceptions;


public class AvatarNotFoundExceptions extends NotFoundException {

    private final static String MESSAGE = "Avatar not found";

    public AvatarNotFoundExceptions() {
        super(MESSAGE);
    }

    public AvatarNotFoundExceptions(String message) {
        super(message);
    }
}
