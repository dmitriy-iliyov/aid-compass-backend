package com.example.exceptions;

import com.example.global_exceptions.BaseNotFoundException;

public class CustomerBaseNotFoundByIdException extends BaseNotFoundException {

    private static String MESSAGE = "Customer not found";

    public CustomerBaseNotFoundByIdException() {}
}
