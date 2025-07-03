package com.aidcompass.contact.core.models.dto.system;

import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.markers.CreateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SystemContactCreateDto(
        @JsonProperty("owner_id")
        UUID ownerId,

        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact

) implements CreateDto { }

