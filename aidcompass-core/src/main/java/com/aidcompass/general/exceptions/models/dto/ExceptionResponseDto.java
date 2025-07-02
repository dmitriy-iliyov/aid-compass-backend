package com.aidcompass.general.exceptions.models.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }
