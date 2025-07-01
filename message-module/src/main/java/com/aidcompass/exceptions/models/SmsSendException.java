package com.aidcompass.exceptions.models;

import com.aidcompass.BaseSendMessageException;
import com.aidcompass.dto.ErrorDto;

public class SmsSendException extends BaseSendMessageException {

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
