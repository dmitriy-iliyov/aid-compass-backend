package com.aidcompass.general.exceptions.gender;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class GenderNotFoundByCode extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Gender not found by code!");
    }
}
