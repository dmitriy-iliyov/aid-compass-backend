package com.aidcompass.enums.gender;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class GenderConvertException extends BaseInvalidInputException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Convert exception!");
    }
}
