package com.aidcompass.contracts;


import com.aidcompass.enums.ContactType;

public record UserInfo(
        String firstName,
        String secondName,
        ContactType type,
        String contact
) { }