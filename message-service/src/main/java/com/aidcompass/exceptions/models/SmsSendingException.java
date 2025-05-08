package com.aidcompass.exceptions.models;

import com.aidcompass.global_exceptions.Exception;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class SmsSendingException extends Exception {

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
