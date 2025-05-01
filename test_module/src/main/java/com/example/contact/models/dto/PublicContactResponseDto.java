package com.example.contact.models.dto;


public record PublicContactResponseDto(
        String type,
        String contact,
        boolean isPrimary
) { }
