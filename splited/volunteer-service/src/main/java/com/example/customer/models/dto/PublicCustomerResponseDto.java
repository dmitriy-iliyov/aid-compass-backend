package com.example.customer.models.dto;

import com.example.contacts.models.ContactsDto;

import java.util.UUID;

public record PublicCustomerResponseDto(
        UUID id,
        String firstName,
        String secondName,
        ContactsDto contacts
) { }
