package com.aidcompass.contact.models.dto;

import com.aidcompass.contact.validation.annotation.Contact;
import com.aidcompass.enums.ContactType;
import com.aidcompass.markers.CreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Contact
public record ContactCreateDto(
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) implements CreateDto { }
