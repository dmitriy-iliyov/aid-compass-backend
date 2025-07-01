package com.aidcompass.general.exceptions.customer;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class CustomerNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("customer", "Customer not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
