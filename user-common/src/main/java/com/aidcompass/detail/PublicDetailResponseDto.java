package com.aidcompass.detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicDetailResponseDto(
        @JsonProperty("about_myself")
        String aboutMyself,

        String address
) { }
