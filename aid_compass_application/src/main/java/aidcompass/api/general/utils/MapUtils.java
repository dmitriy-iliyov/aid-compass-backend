package aidcompass.api.general.utils;


import aidcompass.api.general.models.CustomBindingErrors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Set;

@UtilityClass
public class MapUtils {

    public HashMap<String,String> bindingErrors(@NotNull BindingResult bindingResult){
        HashMap<String,String> bindingErrors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            bindingErrors.put(error.getField(), error.getDefaultMessage());
        });
        return bindingErrors;
    }

    public <T extends CustomBindingErrors> HashMap<String, String> bindingErrorsFromConstraintValidatorContext(
            @NotNull Set<ConstraintViolation<T>> constraintViolationSet){
        HashMap<String,String> bindingErrors = new HashMap<>();
        for(ConstraintViolation<T> setItem : constraintViolationSet)
            bindingErrors.put(setItem.getPropertyPath().toString(), setItem.getMessage());
        return bindingErrors;
    }
}
