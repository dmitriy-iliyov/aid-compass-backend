package com.aidcompass.dto;


import com.aidcompass.enums.ContactType;

public record UserDto(
        String firstName,
        String secondName,
        String lastName,
        ContactType type,
        String contact
) { }