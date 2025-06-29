package com.aidcompass.exceptions;

import com.aidcompass.advice.BaseControllerAdvice;
import com.aidcompass.mapper.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class AuthModuleControllerAdvice extends BaseControllerAdvice {

    public AuthModuleControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}