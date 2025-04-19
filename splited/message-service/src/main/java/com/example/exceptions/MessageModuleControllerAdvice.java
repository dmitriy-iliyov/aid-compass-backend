package com.example.exceptions;

import com.example.advice.BaseControllerAdvice;
import com.example.exceptions.dto.ErrorDto;
import com.example.exceptions.dto.ExceptionResponseDto;
import com.example.mapper.ExceptionMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MessageModuleControllerAdvice extends BaseControllerAdvice {


    public MessageModuleControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<?> handleMailAuthenticationException(MailException mailException, Locale locale) {
        log.error(mailException.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                this.getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(
                new ErrorDto("", "Sorry, problems with our email!"))));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }
}
