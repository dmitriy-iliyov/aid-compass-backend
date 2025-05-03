package com.aidcompass.contact.validation.contact;

import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface ContactValidator {

    boolean isEmailValid(String email);

    boolean isLengthValid(ContactType type, String contact);

    boolean isPhoneNumberValid(String phoneNumber);

    boolean isEmailUnique(String email);

    boolean isEmailUnique(String email, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber);

    void checkUniquesForType(UUID ownerId, ContactUpdateDto contactUpdateDto, List<ErrorDto> errors);
}
