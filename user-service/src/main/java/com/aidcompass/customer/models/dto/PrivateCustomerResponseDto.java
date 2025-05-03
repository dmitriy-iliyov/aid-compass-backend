package com.aidcompass.customer.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PrivateCustomerResponseDto(
        UUID id,

        @JsonProperty("public_data")
        PublicCustomerResponseDto publicData
) { }
