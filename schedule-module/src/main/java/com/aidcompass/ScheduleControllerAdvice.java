package com.aidcompass;

import com.aidcompass.advice.BaseControllerAdvice;
import com.aidcompass.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ScheduleControllerAdvice extends BaseControllerAdvice {

    public ScheduleControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
