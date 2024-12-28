package aidcompass.api.general.exeption;

import aidcompass.api.general.exeption.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleThrowable(Throwable throwable, Model model) {
        ExceptionDto exceptionDto = new ExceptionDto(
                "500",
                "Unexpected Internal Server Error.",
                null);
        log.error(throwable.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDto);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(Exception e){
        ExceptionDto exceptionDto = new ExceptionDto(
                "400",
                e.getMessage(),
                Arrays.toString(e.getStackTrace()));
        log.error("Exception 400: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDto);
    }

//    @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(Exception e){
        ExceptionDto exceptionDto = new ExceptionDto(
                "404",
                e.getMessage(),
                null);
        log.error("Exception 404: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        ExceptionDto exceptionDto = new ExceptionDto(
                "500",
                "Illegal State Exception",
                null);
        log.error("Exception 500: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDto);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
//        ExceptionDto exceptionDto = new ExceptionDto(
//                "400",
//                e.getMessage(),
//                null);
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exceptionDto);
//    }

}
