package com.aidcompass.general.exceptions.gender;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class GenderConvertException extends BaseInvalidInputException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Convert exception!");
    }
}
