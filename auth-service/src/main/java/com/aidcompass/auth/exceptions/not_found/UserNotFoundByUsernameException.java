package com.aidcompass.auth.exceptions.not_found;

import com.aidcompass.common.global_exceptions.UserNotFoundException;

public class UserNotFoundByUsernameException extends UserNotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByUsernameException() {
        super(MESSAGE);
    }


}
