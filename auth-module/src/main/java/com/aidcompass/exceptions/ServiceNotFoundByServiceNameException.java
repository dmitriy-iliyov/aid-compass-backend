package com.aidcompass.exceptions;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ServiceNotFoundByServiceNameException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service", "Service not found by name!");
    }
}
