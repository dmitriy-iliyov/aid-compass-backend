package com.aidcompass.detail.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicDetailResponseDto(
        String address,

        @JsonProperty("working_experience")
        Integer workingExperience,

        @JsonProperty("about_myself")
        String aboutMyself
) { }
