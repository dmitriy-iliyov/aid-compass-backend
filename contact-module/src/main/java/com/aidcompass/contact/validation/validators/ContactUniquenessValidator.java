package com.aidcompass.contact.validation.validators;

import com.aidcompass.markers.UpdateDto;
import com.aidcompass.models.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface ContactUniquenessValidator {
    boolean isEmailUnique(String email);

    boolean isEmailUnique(String email, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber);

    void checkUniquesForType(UUID ownerId, UpdateDto contactUpdateDto, List<ErrorDto> errors);
}
