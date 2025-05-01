package com.example.exceptions;


import com.example.global_exceptions.BaseNotFoundException;

public class AvatarBaseNotFoundExceptions extends BaseNotFoundException {

    private final static String MESSAGE = "Avatar not found";

    public AvatarBaseNotFoundExceptions() {
        super(MESSAGE);
    }

    public AvatarBaseNotFoundExceptions(String message) {
        super(message);
    }
}
