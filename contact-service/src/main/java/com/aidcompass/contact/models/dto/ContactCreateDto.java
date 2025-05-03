package com.aidcompass.contact.models.dto;

import com.aidcompass.contact.validation.common.Contact;
import com.aidcompass.contact_type.models.ContactType;
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
