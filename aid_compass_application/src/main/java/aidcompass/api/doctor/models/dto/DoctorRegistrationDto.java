package aidcompass.api.doctor.models.dto;

import aidcompass.api.doctor.validation.email.UniqueDoctorEmail;
import aidcompass.api.doctor.validation.license_number.UniqueDoctorLicenseNumber;
import aidcompass.api.doctor.validation.number.UniqueDoctorNumber;
import aidcompass.api.general.validation.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationDto{

        @NotBlank(message = "Username shouldn't be empty or blank!")
        @Size(min = 10, max = 40, message = "Should has lengths from 10 to 40 characters!")
        private String username;

        @NotBlank(message = "Email shouldn't be empty or blank!")
        @Size(min = 11, max = 50, message = "Should has lengths from 11 to 50 characters!")
        @Email(message = "Email should be valid!")
        @UniqueDoctorEmail(message = "Email is in use!")
        private String email;

        @NotBlank(message = "Password can't be empty or blank!")
        @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
        private String password;

        @NotBlank(message = "Number can't be empty or blank!")
        @Size(min = 13, max = 17, message = "Number should has lengths from 13 to 17!")
        @PhoneNumber
        @UniqueDoctorNumber(message = "Number should be unique.")
        private String number;

        @NotBlank(message = "License number can't be empty or blank!")
        @UniqueDoctorLicenseNumber(message = "License is in use!")
        private String licenseNumber;

        @NotBlank(message = "Specialization can't be empty or blank.")
        private String specialization;

        @Min(value = 2, message = "Years of experience must be positive!")
        @Max(value = 60, message = "Years of experience exceed the maximum of 60 years!")
        private Integer yearsOfExperience;

        @Size(max = 100, message = "Address can't exceed 100 characters!")
        private String address;

        @Size(max = 255, message = "Achievements can't exceed 255 characters!")
        private String achievements;
}
