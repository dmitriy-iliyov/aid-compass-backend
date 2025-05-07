package com.aidcompass.contact.models.dto.system;

import com.aidcompass.contact.models.dto.markers.CreateDto;
import com.aidcompass.contact.validation.annotation.Contact;
import com.aidcompass.contact_type.models.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Contact
public record SystemContactCreateDto(
        @JsonProperty("owner_id")
        UUID ownerId,

        @JsonProperty("type")
        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @JsonProperty("contact")
        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact
) implements CreateDto { }

