package com.aidcompass.exceptions.models;

import com.aidcompass.BaseSendMessageException;
import com.aidcompass.dto.ErrorDto;

public class SendConfirmationMessageException extends BaseSendMessageException {

    private final ErrorDto errorDto = new ErrorDto("", "Error when sending message!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
