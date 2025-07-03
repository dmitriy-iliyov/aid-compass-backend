package com.aidcompass.contact.core.validation.validators;

import com.aidcompass.ContactType;

public interface FormatValidator {

    boolean isEmailValid(String email);

    boolean isLengthValid(ContactType type, String contact);

    boolean isPhoneNumberValid(String phoneNumber);
}
