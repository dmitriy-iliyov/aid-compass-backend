package com.aidcompass.contact.core.models.dto.system;

import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.markers.UpdateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record SystemContactUpdateDto(
        @JsonProperty("owner_id")
        UUID ownerId,

        @NotNull(message = "Contact id shouldn't be null!")
        @Positive(message = "Contact id should be positive!")
        Long id,

        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact,

        @JsonProperty("is_primary")
        @NotNull(message = "Primary flag shouldn't be empty or blank!")
        boolean isPrimary
) implements UpdateDto { }
