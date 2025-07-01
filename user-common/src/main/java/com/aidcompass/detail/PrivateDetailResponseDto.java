package com.aidcompass.detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrivateDetailResponseDto(
        Long id,

        @JsonProperty("about_myself")
        String aboutMyself,

        String address
) { }
