package com.aidcompass.exceptions.models;

import com.aidcompass.models.BaseSendMessageException;
import com.aidcompass.models.dto.ErrorDto;

public class SmsSendException extends BaseSendMessageException {

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
