package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInternalServiceException;
import com.aidcompass.models.dto.ErrorDto;

public class CsrfTokenMaskingException extends BaseInternalServiceException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Unexpected exception when masking csrf token!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
