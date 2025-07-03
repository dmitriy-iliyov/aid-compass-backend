package com.aidcompass;

import com.aidcompass.general.exceptions.BaseControllerAdvice;
import com.aidcompass.general.exceptions.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ScheduleControllerAdvice extends BaseControllerAdvice {

    public ScheduleControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
