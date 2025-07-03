package com.aidcompass.general.exceptions.mapper;

import com.aidcompass.general.exceptions.models.dto.ExceptionResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class ExceptionMapperImpl implements ExceptionMapper {

    @Override
    public ExceptionResponseDto toDto(Exception exception) {
        if ( exception == null ) {
            return null;
        }

        String message = null;

        message = exception.getMessage();

        String code = null;
        String description = null;

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto( code, message, description );

        return exceptionResponseDto;
    }
}
