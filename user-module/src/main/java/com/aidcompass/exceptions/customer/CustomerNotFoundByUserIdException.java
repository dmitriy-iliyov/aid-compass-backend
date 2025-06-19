package com.aidcompass.exceptions.customer;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class CustomerNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("customer", "Customer not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
