package com.aidcompass.exceptions.not_found;

import com.aidcompass.global_exceptions.UserNotFoundException;

public class UserNotFoundByUsernameException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByUsernameException() {
        super(MESSAGE);
    }


}
