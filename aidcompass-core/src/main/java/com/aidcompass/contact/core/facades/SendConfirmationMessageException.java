package com.aidcompass.contact.core.facades;

import com.aidcompass.general.exceptions.models.Exception;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class SendConfirmationMessageException extends Exception {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("sending_message", "Error when sending message!");
    }
}
