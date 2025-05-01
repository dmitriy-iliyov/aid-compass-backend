package com.example.exceptions;

import com.example.advice.BaseControllerAdvice;
import com.example.mapper.ExceptionMapper;
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