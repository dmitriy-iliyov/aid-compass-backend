package aidcompass.api.doctor.models.dto;

import aidcompass.api.doctor.validation.email.UniqueDoctorEmail;
import aidcompass.api.doctor.validation.license_number.UniqueDoctorLicenseNumber;
import aidcompass.api.doctor.validation.number.UniqueDoctorNumber;
import aidcompass.api.general.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationDto{

        @NotBlank(message = "Username shouldn't be empty or blank!")
        @Size(min = 10, max = 30)
        private String username;

        @NotBlank(message = "Email shouldn't be empty or blank!")
        @Size(min = 11, max = 50)
        @Email(message = "Email should be valid!")
        @UniqueDoctorEmail
        private String email;

        @NotBlank(message = "Password can't be empty or blank!")
        @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
        private String password;

        @NotBlank(message = "Number can't be empty or blank!")
        @Size(min = 13, max = 17, message = "Number should has valid lengths!")
        @PhoneNumber
        @UniqueDoctorNumber
        private String number;

        @NotBlank(message = "License number can't be empty or blank!")
        @UniqueDoctorLicenseNumber
        private String licenseNumber;

        @NotBlank(message = "Specialization can't be empty or blank")
        private String specialization;

        @Max(value = 100, message = "Years of experience exceed max 2 digits!")
        private Integer yearsOfExperience;

        @Size(max = 255, message = "Address can't exceed 255 characters!")
        private String address;

        @Size(max = 255, message = "Achievements can't exceed 255 characters!")
        private String achievements;
}
