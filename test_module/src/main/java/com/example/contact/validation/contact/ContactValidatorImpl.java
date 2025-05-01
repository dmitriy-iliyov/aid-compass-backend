package com.example.contact.validation.contact;

import com.example.contact.models.dto.ContactUpdateDto;
import com.example.contact.services.SystemContactService;
import com.example.contact_type.models.ContactType;
import com.aidcompass.common.global_exceptions.BaseNotFoundException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class ContactValidatorImpl implements ContactValidator {

    private final SystemContactService service;
    private final static short MIN_EMAIL_LENGTH = 7;
    private final static short MAX_EMAIL_LENGTH = 50;
    private final Pattern emailRegx = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
//    private final Pattern phoneNumberRegx = Pattern.compile("^[+]\\d{3}-?\\d{2}-?\\d{3}-?\\d{2}-?\\d{2}$");
    private final Pattern phoneNumberRegx = Pattern.compile("^[+]\\d{12}$");


    @Override
    public boolean isEmailValid(String email) {
        return emailRegx.matcher(email).matches();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !service.existsByContactTypeAndContact(ContactType.EMAIL, email);
    }

    @Override
    public boolean isLengthValid(ContactType type, String contact) {
        if (type == ContactType.EMAIL) {
            return MIN_EMAIL_LENGTH <= contact.length() && contact.length() <= MAX_EMAIL_LENGTH;
        }
        return false;
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumberRegx.matcher(phoneNumber).matches();
    }

    @Override
    public boolean isEmailUnique(String email, UUID ownerId) {
        try {
            return service.findByContact(email).ownerId() == ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId) {
        try {
            return service.findByContact(phoneNumber).ownerId() == ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !service.existsByContactTypeAndContact(ContactType.PHONE_NUMBER, phoneNumber);
    }

    /**
     * Validates the uniqueness of a contact based on its type and value for the given owner.
     *
     * @param ownerId ID of the contact owner
     * @param contactUpdateDto DTO containing updated contact information
     * @param errors List to accumulate validation errors
     */
    @Override
    public void checkUniquesForType(UUID ownerId, ContactUpdateDto contactUpdateDto, List<ErrorDto> errors) {
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
