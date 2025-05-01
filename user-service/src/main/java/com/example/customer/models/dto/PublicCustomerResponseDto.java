package com.example.customer.models.dto;

import com.example.contact.models.dto.ContactsCreateDto;

import java.util.UUID;

public record PublicCustomerResponseDto(
        UUID id,
        String firstName,
        String secondName,
        ContactsCreateDto contacts
) { }
