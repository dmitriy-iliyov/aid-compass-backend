package com.example.customer.models.dto;

import com.example.clients.user.dto.UserResponseDto;

import java.util.UUID;

public record PrivateCustomerResponseDto(
        UUID id,
        PublicCustomerResponseDto publicData,
        UserResponseDto privateData
) { }
