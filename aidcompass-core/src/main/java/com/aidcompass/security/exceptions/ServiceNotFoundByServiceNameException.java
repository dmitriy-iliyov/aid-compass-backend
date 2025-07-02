package com.aidcompass.security.exceptions;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class ServiceNotFoundByServiceNameException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service", "Service not found by name!");
    }
}
