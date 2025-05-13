package com.aidcompass.customer.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PublicCustomerResponseDto(
        UUID id,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        @JsonProperty("last_name")
        String lastName
) { }
