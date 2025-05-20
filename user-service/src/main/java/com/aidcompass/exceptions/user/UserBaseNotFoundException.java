package com.aidcompass.exceptions.user;


import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class UserBaseNotFoundException extends BaseNotFoundException {

    private final static String MESSAGE = "User not found";

    private final ErrorDto errorDto = new ErrorDto("user", MESSAGE);

    public UserBaseNotFoundException() {
        super(MESSAGE);
    }

    public UserBaseNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
