package com.aidcompass.exceptions.customer;


import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class CustomerNotFoundByIdException extends BaseNotFoundException {

    private static String MESSAGE = "Customer not found";



    public CustomerNotFoundByIdException() {}


    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
