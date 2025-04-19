package com.example.exceptions;


import com.example.exceptions.dto.ErrorDto;
import com.example.exceptions.dto.ExceptionResponseDto;
import com.example.exceptions.mapper.ExceptionMapper;
import com.example.utils.ErrorUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final ExceptionMapper exceptionMapper;
    private final MessageSource messageSource;

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            org.springframework.web.HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<?> handleNoHandlerOrNoResourceFoundException(java.lang.Exception e) {
        String url = null;
        if (e instanceof NoHandlerFoundException) {
            url = ((NoHandlerFoundException) e).getRequestURL();
        }
        if (e instanceof NoResourceFoundException) {
            url = ((NoResourceFoundException) e).getResourcePath();
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(
                        "404",
                        "Not Found",
                        "The " + url + " was not found.")
                );
    }

//    @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
//    public ResponseEntity<?> handleAuthorizationDeniedException() {
//        // когда истекает кука то пользователя нужно перенаправлять на регистраию
//        // существует перехватчик чтоб скрывать наличие приватных/фдминских маршрутов
//        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
//                "404",
//                "Not Found",
//                "The resource was not found.");
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exceptionDto);
//    }

//    @ExceptionHandler(Throwable.class)
//    public ResponseEntity<?> handleThrowable(Throwable throwable) {
//        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
//                "500",
//                "Unexpected Internal Server Error.",
//                null);
//        log.error(throwable.getMessage());
//        System.out.println(Arrays.toString(throwable.getStackTrace()));
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(exceptionDto);
//    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<?> handleMailAuthenticationException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponseDto(
                        "500",
                        "Problems with our email."
                        , null)
                );
    }

    @ExceptionHandler({DataIntegrityViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(java.lang.Exception e){
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "400",
                e.getMessage(),
                Arrays.toString(e.getStackTrace()));
        log.error("Exception 400: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionMapper.toDto(e));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "500",
                "Illegal State Exception",
                null);
        log.error("Exception 500: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDto);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
//        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
//                "400",
//                e.getMessage(),
//                null);
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exceptionDto);
//    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String pN = e.getParameterName();
        String message = String.valueOf(pN.charAt(0)).toUpperCase() + pN.substring(1) + " should be present!";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto("404", message, null));
    }


    //validation exception handling
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("errors", ErrorUtils.toErrorDtoList(e.getValueResults()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("errors", ErrorUtils.toErrorDtoList(e.getBindingResult()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> bindingResult = e.getConstraintViolations();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("errors", ErrorUtils.toErrorDtoList(bindingResult));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("errors", new ErrorDto(e.getName(), "Has invalid type."));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

//    @ExceptionHandler(CookieJwtExpired.class)
//    public HttpServletResponse handleCookieJwtExpired(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/users/login");
//        return response;
//    }


    //RedisSystemException
}