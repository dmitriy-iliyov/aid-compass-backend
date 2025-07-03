package com.aidcompass.security.exceptions;

import com.aidcompass.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class CsrfTokenMaskingException extends BaseInternalServerException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Unexpected exception when masking csrf token!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
