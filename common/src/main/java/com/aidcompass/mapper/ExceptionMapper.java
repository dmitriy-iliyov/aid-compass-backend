package com.aidcompass.mapper;

import com.aidcompass.global_exceptions.dto.ExceptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExceptionMapper {
    ExceptionResponseDto toDto(Exception exception);
}
