package com.aidcompass.exceptions;


import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class AvatarNotFoundExceptions extends BaseNotFoundException {

    private final static String MESSAGE = "Avatar not found";
    private final ErrorDto errorDto = new ErrorDto("avatar", MESSAGE);


    public AvatarNotFoundExceptions() {
        super(MESSAGE);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
