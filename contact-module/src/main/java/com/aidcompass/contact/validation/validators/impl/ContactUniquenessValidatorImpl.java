package com.aidcompass.contact.validation.validators.impl;

import com.aidcompass.contracts.ContactServiceSyncOrchestrator;
import com.aidcompass.enums.ContactType;
import com.aidcompass.markers.UpdateDto;
import com.aidcompass.contact.validation.validators.ContactUniquenessValidator;
import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class ContactUniquenessValidatorImpl implements ContactUniquenessValidator {

    private final ContactServiceSyncOrchestrator facade;


    @Override
    public boolean isEmailUnique(String email) {
        // check in userUnconfirmedRepo
        return !facade.existsByContactTypeAndContact(ContactType.EMAIL, email);
    }

    @Override
    public boolean isEmailUnique(String email, UUID ownerId) {
        try {
            // check in userUnconfirmedRepo
            return facade.findByContact(email).getOwnerId() != ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId) {
        try {
            return facade.findByContact(phoneNumber).getOwnerId() != ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !facade.existsByContactTypeAndContact(ContactType.PHONE_NUMBER, phoneNumber);
    }

    /**
     * Validates the uniqueness of a contact based on its type and value for the given owner.
     *
     * @param ownerId ID of the contact owner
     * @param contactUpdateDto DTO containing updated contact information
     * @param errors List to accumulate validation errors
     */
    @Override
    public void checkUniquesForType(UUID ownerId, UpdateDto contactUpdateDto, List<ErrorDto> errors) {
        String contact = contactUpdateDto.contact();
        ContactType type = contactUpdateDto.type();

        if (type == ContactType.EMAIL) {
            if (!this.isEmailUnique(contact, ownerId)) {
                errors.add(new ErrorDto("email", "Email is in use!"));
            }
        } else if (type == ContactType.PHONE_NUMBER) {
            if (!this.isPhoneNumberUnique(contact, ownerId)) {
                errors.add(new ErrorDto("phone_number", "Phone number is in use!"));
            }
        } else {
            errors.add(new ErrorDto("type", "Contact type is invalid!"));
        }
    }
}
