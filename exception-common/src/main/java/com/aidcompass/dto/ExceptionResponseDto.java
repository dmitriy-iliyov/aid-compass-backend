package com.aidcompass.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }
