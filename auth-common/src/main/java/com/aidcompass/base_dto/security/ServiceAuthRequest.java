package com.aidcompass.base_dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SystemAuthRequest(
    @JsonProperty("service_name")
    @NotBlank(message = "Service name shouldn't be empty or blank!")
    String serviceName,

    @NotBlank(message = "Service password shouldn't be empty or blank!")
    String password
) { }
