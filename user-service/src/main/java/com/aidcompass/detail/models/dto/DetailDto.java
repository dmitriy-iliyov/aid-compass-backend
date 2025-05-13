package com.aidcompass.detail.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

public record DetailDto(
        @JsonProperty("working_experience")
        @Digits(integer = 2, fraction = 0, message = "Working experience can't contain more than 2 digits!")
        Integer workingExperience,

        @JsonProperty("about_myself")
        @Size(max = 235, message = "About myself can't contains more then 235 characters!")
        String aboutMyself
) { }
