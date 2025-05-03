package com.aidcompass.doctor.models.dto;

import com.aidcompass.doctor.validation.doctor_update.ValidDoctorUpdate;
import jakarta.validation.constraints.*;

@ValidDoctorUpdate
public record DoctorUpdateDto(

        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "First name should contain only Ukrainian")
        String firstName,

        @NotBlank(message = "Second name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "Second name should contain only Ukrainian")
        String secondName,

        @NotBlank(message = "License number can't be empty or blank!")
        //@UniqueDoctorLicenseNumber(message = "License is in use!")
        String licenseNumber,

        @NotBlank(message = "Specialization can't be empty or blank.")
        String specialization,

        @Min(value = 0, message = "Years of experience must be positive!")
        @Max(value = 60, message = "Years of experience exceed the maximum of 60 years!")
        Integer yearsOfExperience,

        @Size(max = 100, message = "Address can't exceed 100 characters!")
        String address,

        @Size(max = 255, message = "Achievements can't exceed 255 characters!")
        String achievements
) { }
