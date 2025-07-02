package com.aidcompass.contact.core.models.dto;

import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.markers.CreateDto;
import com.aidcompass.contact.core.validation.annotation.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Contact
public record ContactCreateDto(
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) implements CreateDto { }
