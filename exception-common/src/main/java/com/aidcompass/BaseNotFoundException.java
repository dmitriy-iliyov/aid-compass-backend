package com.aidcompass;


public abstract class BaseNotFoundException extends Exception {

    private final static String MESSAGE = "Not found";

    public BaseNotFoundException() {
        super(MESSAGE);
    }

    public BaseNotFoundException(String message) {
        super(MESSAGE + ": " + message);
    }
}
