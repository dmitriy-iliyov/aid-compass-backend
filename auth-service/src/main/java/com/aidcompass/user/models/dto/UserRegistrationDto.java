package com.aidcompass.user.models.dto;


import com.aidcompass.user.validation.email.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRegistrationDto(

    @NotBlank(message = "Email shouldn't be empty or blank!")
    @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
    @Email(message = "Email should be valid!")
    @UniqueEmail(message = "Email is in use!")
    String email,

    @NotBlank(message = "Password can't be empty or blank!")
    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
    String password
) { }
