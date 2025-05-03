package com.aidcompass.contact.validation.count;

import com.aidcompass.contact.models.dto.ContactCreateDto;

import java.util.List;
import java.util.UUID;

public interface ContactCountValidator {

    boolean hasSpaceForContact(UUID ownerId, ContactCreateDto contact);

    boolean hasSpaceForContacts(UUID ownerId, List<ContactCreateDto> contacts);

    boolean hasSpaceForEmails(UUID ownerId, long addCount);

    boolean hasSpaceForPhoneNumbers(UUID ownerId, long addCount);
}
