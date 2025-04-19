package com.example.contacts.models;

import com.example.contacts.validation.contacts.Contacts;
import com.example.contacts.validation.update.ContactsUpdate;

import java.util.List;
import java.util.Map;

@ContactsUpdate
public record ContactsDto(
        @Contacts
        Map<String, List<String>> contacts
) { }
