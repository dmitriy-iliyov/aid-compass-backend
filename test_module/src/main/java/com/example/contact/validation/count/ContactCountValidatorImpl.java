package com.example.contact.validation.count;

import com.example.contact.models.dto.ContactCreateDto;
import com.example.contact.services.SystemContactService;
import com.example.contact_type.models.ContactType;
import com.example.exceptions.invalid_input.InvalidContactTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContactCountValidatorImpl implements ContactCountValidator {

    private final SystemContactService service;
    private static final long MAX_EMAIL_COUNT = 3;
    private static final long MAX_PHONE_NUMBER_COUNT = 2;


    /**
     * Checks if there is space to add a new contact of the specified type for the given owner.
     *
     * @param ownerId ID of the contact owner
     * @param contact DTO containing contact information
     * @return true if there is space to add a new contact of the specified type, false otherwise
     * @throws InvalidContactTypeException if the contact type in the passed DTO does not exist
     */
    @Override
    public boolean hasSpaceForContact(UUID ownerId, ContactCreateDto contact) {
        if (contact.type() == ContactType.EMAIL) {
            return hasSpaceForEmails(ownerId, 1);
        } else if (contact.type() == ContactType.PHONE_NUMBER) {
            return hasSpaceForPhoneNumbers(ownerId, 1);
        } else {
            throw new InvalidContactTypeException();
        }
    }

    @Override
    public boolean hasSpaceForContacts(UUID ownerId, List<ContactCreateDto> contacts) {
        long emailCount = contacts.stream().filter(contact -> contact.type().equals(ContactType.EMAIL)).count();
        long phoneNumberCount = contacts.stream().filter(contact -> contact.type().equals(ContactType.PHONE_NUMBER)).count();
        return hasSpaceForEmails(ownerId, emailCount) && hasSpaceForPhoneNumbers(ownerId, phoneNumberCount);
    }

    @Override
    public boolean hasSpaceForEmails(UUID ownerId, long addCount) {
        long currentCount = service.countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
        return currentCount + addCount <= MAX_EMAIL_COUNT;
    }

    @Override
    public boolean hasSpaceForPhoneNumbers(UUID ownerId, long addCount) {
        long currentCount = service.countByOwnerIdAndContactType(ownerId, ContactType.PHONE_NUMBER);
        return currentCount + addCount <= MAX_PHONE_NUMBER_COUNT;
    }
}
