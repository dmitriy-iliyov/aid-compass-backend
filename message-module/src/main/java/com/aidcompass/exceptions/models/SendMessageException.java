package com.aidcompass.exceptions.models;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class SendMessageException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
