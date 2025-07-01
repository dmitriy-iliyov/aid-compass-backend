package com.aidcompass;

import com.aidcompass.exceptions.BaseControllerAdvice;
import com.aidcompass.exceptions.mapper.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserControllerAdvice extends BaseControllerAdvice {

    public UserControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource){
        super(exceptionMapper, messageSource);
    }
}
