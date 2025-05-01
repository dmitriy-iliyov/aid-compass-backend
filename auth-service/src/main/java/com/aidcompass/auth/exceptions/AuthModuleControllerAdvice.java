package com.aidcompass.auth.exceptions;

import com.aidcompass.common.advice.BaseControllerAdvice;
import com.aidcompass.common.mapper.ExceptionMapper;
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