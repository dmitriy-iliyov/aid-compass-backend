package com.aidcompass.doctor.models.dto.doctor;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DoctorUpdateDto(

        @JsonProperty("first_name")
        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "First name should contain only Ukrainian!"
        )
        String firstName,

        @JsonProperty("second_name")
        @NotBlank(message = "Second name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "Second name should contain only Ukrainian!"
        )
        String secondName,

        @JsonProperty("last_name")
        @NotBlank(message = "Last name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "Last name should contain only Ukrainian!"
        )
        String lastName,

        @JsonProperty("specializations")
        List<DoctorSpecialization> specializations
) { }
