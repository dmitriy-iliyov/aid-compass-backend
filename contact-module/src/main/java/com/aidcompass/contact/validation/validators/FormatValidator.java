package com.aidcompass.contact.validation.validators;

import com.aidcompass.enums.ContactType;

public interface FormatValidator {

    boolean isEmailValid(String email);

    boolean isLengthValid(ContactType type, String contact);

    boolean isPhoneNumberValid(String phoneNumber);
}
