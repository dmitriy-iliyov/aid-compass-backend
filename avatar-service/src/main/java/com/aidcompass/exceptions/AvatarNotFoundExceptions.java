package com.aidcompass.exceptions;


import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class AvatarNotFoundExceptions extends BaseNotFoundException {

    private final static String MESSAGE = "Avatar not found";
    private final ErrorDto errorDto = new ErrorDto("avatar", MESSAGE);


    public AvatarNotFoundExceptions() {
        super(MESSAGE);
    }

    public AvatarNotFoundExceptions(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
