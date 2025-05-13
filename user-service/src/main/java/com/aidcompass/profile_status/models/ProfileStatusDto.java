package com.aidcompass.profile_status.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProfileStatusDto(
        Integer id,

        @JsonProperty("profile_status")
        ProfileStatus status
) { }
