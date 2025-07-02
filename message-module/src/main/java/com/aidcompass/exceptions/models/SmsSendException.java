package com.aidcompass.exceptions.models;

import com.aidcompass.general.exceptions.models.BaseSendMessageException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class SmsSendException extends BaseSendMessageException {

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
