package com.aidcompass.contact.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ContactUpdateDtoList(
        @JsonProperty("contacts")
        @NotEmpty(message = "List of contacts can't be empty!")
        @Valid
        List<ContactUpdateDto> contacts
) { }
