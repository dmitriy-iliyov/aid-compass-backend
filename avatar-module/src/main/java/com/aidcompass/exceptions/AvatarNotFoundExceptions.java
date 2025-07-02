package com.aidcompass.exceptions;


import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

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
