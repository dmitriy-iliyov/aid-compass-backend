package com.aidcompass.common.global_exceptions.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }
