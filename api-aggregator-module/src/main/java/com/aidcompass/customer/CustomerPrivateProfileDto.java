package com.aidcompass.customer;


import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import com.aidcompass.dto.PrivateContactResponseDto;
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
