package com.aidcompass.contact.models.dto;

import com.aidcompass.contact.validation.common.Contact;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.validation.ValidEnum;
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
