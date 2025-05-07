package com.aidcompass.contact.validation.validators;

import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.markers.CreateDto;

import java.util.List;
import java.util.UUID;

public interface CountValidator {

    boolean hasSpaceForContact(UUID ownerId, CreateDto contact);

    boolean hasSpaceForContacts(UUID ownerId, List<CreateDto> contacts);

    boolean hasSpaceForEmails(UUID ownerId, long addCount);

    boolean hasSpaceForPhoneNumbers(UUID ownerId, long addCount);
}
