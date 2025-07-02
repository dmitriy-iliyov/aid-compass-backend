package com.aidcompass.api.customer;


import com.aidcompass.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomerPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("customer_profile")
        PrivateCustomerResponseDto fullDoctor,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts
) { }
