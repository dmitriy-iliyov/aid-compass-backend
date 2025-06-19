package com.aidcompass.base_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record SystemConfirmationRequestDto(
        @JsonProperty("contact")
        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) { }
