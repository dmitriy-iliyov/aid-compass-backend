package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class GenderConvertException extends BaseInvalidInputException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Convert exception!");
    }
}
