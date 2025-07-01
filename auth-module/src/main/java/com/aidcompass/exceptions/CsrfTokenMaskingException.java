package com.aidcompass.exceptions;

import com.aidcompass.BaseInternalServerException;
import com.aidcompass.dto.ErrorDto;

public class CsrfTokenMaskingException extends BaseInternalServerException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Unexpected exception when masking csrf token!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
