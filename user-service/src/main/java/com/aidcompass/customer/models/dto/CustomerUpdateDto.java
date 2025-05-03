package com.aidcompass.customer.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerUpdateDto(
        @JsonProperty("first_name")
        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "First name should contain only Ukrainian")
        String firstName,

        @JsonProperty("second_name")
        @NotBlank(message = "Second name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "Second name should contain only Ukrainian")
        String secondName
) { }
