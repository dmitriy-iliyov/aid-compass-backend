package com.aidcompass.exceptions.models;

import com.aidcompass.general.exceptions.models.BaseSendMessageException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class SendConfirmationMessageException extends BaseSendMessageException {

    private final ErrorDto errorDto = new ErrorDto("sending_message", "Error when sending message!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
