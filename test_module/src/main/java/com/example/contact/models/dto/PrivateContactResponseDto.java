package com.example.contact.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record PrivateContactResponseDto(
        Long id,
        String type,
        String contact,

        @JsonProperty("is_primary")
        boolean isPrimary,

        @JsonProperty("is_confirmed")
        boolean isConfirmed
) { }
