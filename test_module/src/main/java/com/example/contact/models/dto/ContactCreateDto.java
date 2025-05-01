package com.example.contact.models.dto;

import com.example.contact.validation.common.Contact;
import com.example.contact_type.models.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Contact
public record ContactCreateDto(

        @JsonProperty("type")
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @JsonProperty("contact")
        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) { }
