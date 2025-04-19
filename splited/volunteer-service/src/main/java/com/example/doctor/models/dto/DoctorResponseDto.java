package com.example.doctor.models.dto;

public record DoctorResponseDto(
        String licenseNumber,
        String specialization,
        Integer yearsOfExperience,
        String address,
        String achievements,
        String profilePictureUrl
){ }
