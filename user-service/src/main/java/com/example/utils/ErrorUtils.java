package com.example.utils;

import com.aidcompass.common.global_exceptions.dto.ErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.ParameterValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class ErrorUtils {

    public List<ErrorDto> toErrorDtoList(@NotNull BindingResult bindingResult) {
        List<ErrorDto> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            if (!Objects.requireNonNull(error.getDefaultMessage()).isBlank()) {
                errors.add(new ErrorDto(error.getField(), Objects.requireNonNull(error.getDefaultMessage())));
            }
        });
        return errors;
    }

    public List<ErrorDto> toErrorDtoList(List<ParameterValidationResult> validationResult) {
        List<ErrorDto> errors = new ArrayList<>();
        validationResult.forEach(result -> {
            result.getResolvableErrors().forEach(error -> {
                if (!Objects.requireNonNull(error.getDefaultMessage()).isBlank()) {
                    String[] parts = Objects.requireNonNull(error.getCodes())[0].split("\\.");
                    if (!error.getDefaultMessage().isBlank()) {
                        errors.add(new ErrorDto(parts[parts.length - 1], error.getDefaultMessage()));
                    }
                }
            });
        });
        return errors;
    }

//    public <T extends CustomValidatable> List<ErrorDto> toErrorDtoList(@NotNull Set<ConstraintViolation<T>> bindingResult) {
//        List<ErrorDto> result = new ArrayList<>();
//        for (ConstraintViolation<T> item : bindingResult)
//            if (!Objects.equals(item.getMessage(), "")) {
//                result.add(new ErrorDto(item.getPropertyPath().toString(), item.getMessage()));
//            }
//        return result;
//    }

    public List<ErrorDto> toErrorDtoList(@NotNull Set<ConstraintViolation<?>> bindingResult) {
        List<ErrorDto> errors = new ArrayList<>();
        for (ConstraintViolation<?> item : bindingResult)
            if (!item.getMessage().isBlank()) {
                errors.add(new ErrorDto(item.getPropertyPath().toString(), item.getMessage()));
            }
        return errors;
    }
}
