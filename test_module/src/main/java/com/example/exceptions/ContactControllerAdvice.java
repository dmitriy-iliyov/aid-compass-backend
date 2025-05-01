package com.example.exceptions;

import com.aidcompass.common.advice.BaseControllerAdvice;
import com.example.exceptions.invalid_input.InvalidContactUpdateException;
import com.example.exceptions.invalid_input.InvalidInputExceptionList;
import com.aidcompass.common.mapper.ExceptionMapper;
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

    @ExceptionHandler(InvalidInputExceptionList.class)
    public ResponseEntity<?> handleInvalidContactUpdateException(InvalidContactUpdateException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                this.getMessageSource().getMessage("400", null, "error.400", locale));;
        problemDetail.setProperty("properties", Map.of("errors", e.getErrorDtoList()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }
}
