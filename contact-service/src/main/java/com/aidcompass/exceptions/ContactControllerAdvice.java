package com.aidcompass.exceptions;

import com.aidcompass.advice.BaseControllerAdvice;
import com.aidcompass.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.global_exceptions.BaseInvalidInputExceptionList;
import com.aidcompass.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ContactControllerAdvice extends BaseControllerAdvice {

    public ContactControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
