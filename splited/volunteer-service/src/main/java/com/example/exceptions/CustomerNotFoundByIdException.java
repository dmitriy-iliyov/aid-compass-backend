package com.example.exceptions;

public class CustomerNotFoundByIdException extends NotFoundException {

    private static String MESSAGE = "Customer not found";

    public CustomerNotFoundByIdException() {}
}
