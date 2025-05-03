package com.aidcompass.doctor.models.dto;

import com.aidcompass.doctor.validation.doctor_update.ValidDoctorUpdate;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@ValidDoctorUpdate
public class SystemDoctorUpdateDto {

    @NotNull(message = "Id shouldn't be null!")
    @Positive(message = "Invalid value!")
    private Long id;

    @NotBlank(message = "License number can't be empty or blank!")
    private String licenseNumber;

    @NotBlank(message = "Specialization can't be empty or blank")
    private String specialization;

    @Min(value = 2, message = "Years of experience must be positive!")
    @Max(value = 60, message = "Years of experience exceed the maximum of 60 years!")
    private Integer yearsOfExperience;

    @Size(max = 255, message = "Address can't exceed 255 characters!")
    private String address;

    @Size(max = 255, message = "Achievements can't exceed 255 characters!")
    private String achievements;
}
