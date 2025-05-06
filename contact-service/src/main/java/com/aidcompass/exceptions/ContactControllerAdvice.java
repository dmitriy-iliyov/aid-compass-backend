package com.aidcompass.exceptions;

import com.aidcompass.advice.BaseControllerAdvice;
import com.aidcompass.exceptions.invalid_input.InvalidContactUpdateException;
import com.aidcompass.exceptions.invalid_input.InvalidInputExceptionList;
import com.aidcompass.global_exceptions.Exception;
import com.aidcompass.mapper.ExceptionMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
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
                this.getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", e.getErrorDtoList()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }
}
