package com.example.contact.models.dto;

import com.example.contact.validation.common.Contact;
import com.example.contact_type.models.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Contact
public record ContactUpdateDto(

        @JsonProperty("id")
        @NotNull(message = "Contact id shouldn't be null!")
        @Positive(message = "Contact id should be positive!")
        Long id,

        @JsonProperty("type")
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @JsonProperty("contact")
        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact,

        @JsonProperty("is_primary")
        @NotNull(message = "Primary flag shouldn't be empty or blank!")
        Boolean isPrimary
) { }
