package com.aidcompass.enums.gender;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class GenderNotFoundByCode extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Gender not found by code!");
    }
}
