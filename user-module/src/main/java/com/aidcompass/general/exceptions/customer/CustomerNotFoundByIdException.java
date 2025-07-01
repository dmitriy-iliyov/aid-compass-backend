package com.aidcompass.general.exceptions.customer;


import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class CustomerNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("customer", "Customer not found");


    public CustomerNotFoundByIdException() {}


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
