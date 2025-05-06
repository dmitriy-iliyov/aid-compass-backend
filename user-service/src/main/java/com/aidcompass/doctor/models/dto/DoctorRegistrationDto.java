package com.aidcompass.doctor.models.dto;

import com.aidcompass.doctor.validation.license_number.UniqueDoctorLicense;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record DoctorRegistrationDto (

        @JsonProperty("full_name")
        @NotBlank(message = "Full name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "First name should contain only Ukrainian!")
        String fullName,

        @JsonProperty("license_number")
        @NotBlank(message = "License number can't be empty or blank!")
        @UniqueDoctorLicense(message = "License is in use!")
        String licenseNumber
) { }
