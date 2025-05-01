package com.example.global_exceptions.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }
