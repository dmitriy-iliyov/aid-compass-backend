package com.aidcompass.detail.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicDetailResponseDto(
        @JsonProperty("about_myself")
        String aboutMyself,

        String address
) { }
