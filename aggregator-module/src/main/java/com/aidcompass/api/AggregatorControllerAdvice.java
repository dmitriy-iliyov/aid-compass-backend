package com.aidcompass.api;

import com.aidcompass.general.exceptions.BaseControllerAdvice;
import com.aidcompass.general.exceptions.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class AggregatorControllerAdvice extends BaseControllerAdvice {
    public AggregatorControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource){
        super(exceptionMapper, messageSource);
    }
}
