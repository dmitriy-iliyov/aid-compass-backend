package com.aidcompass.exceptions;

import com.aidcompass.general.exceptions.BaseControllerAdvice;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;
import com.aidcompass.general.exceptions.mapper.ExceptionMapper;
import jakarta.mail.MessagingException;
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
    public ResponseEntity<?> handleMailException(MailException mailException, Locale locale) {
        log.error(mailException.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                this.getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(
                new ErrorDto("", "Sorry, problems with our email!"))));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException messagingException, Locale locale) {
        log.error(messagingException.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                this.getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(
                new ErrorDto("", "Error when sending email, please try again!"))));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }
}
