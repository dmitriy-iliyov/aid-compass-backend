package com.aidcompass.exceptions.user;

public class UserBaseNotFoundByIdException extends UserBaseNotFoundException {

    public UserBaseNotFoundByIdException(String message) {
        super(message);
    }
}
