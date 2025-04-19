package com.example.contacts.validation.contacts;

import com.example.contacts.models.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ContactsValidator implements ConstraintValidator<Contacts, Map<String, List<String>>> {

    private final Pattern emailRegx = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private final Pattern phoneNumberRegx = Pattern.compile("^[+]\\d{3}-?\\d{2}-?\\d{3}-?\\d{2}-?\\d{2}$");


    @Override
    public boolean isValid(Map<String, List<String>> contacts, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasErrors = false;
        for (String key: contacts.keySet()) {
            ContactType contactType = ContactType.valueOf(key);
            if (contactType == ContactType.EMAIL) {
                hasErrors = hasEmailsErrors(contacts.get(key), constraintValidatorContext);
            } else if (contactType == ContactType.PHONE_NUMBER) {
                hasErrors = hasPhoneNumbersErrors(contacts.get(key), constraintValidatorContext);
            }
        }
        return !hasErrors;
    }

    private boolean hasEmailsErrors(List<String> emails, ConstraintValidatorContext constraintValidatorContext) {
        for (String email: emails) {
            if (email == null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email shouldn't be empty or blank!")
                        .addPropertyNode("email").addConstraintViolation();
                return true;
            } else {
                if (!emailRegx.matcher(email).matches()) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Email should be valid!")
                            .addPropertyNode("email").addConstraintViolation();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPhoneNumbersErrors(List<String> phoneNumbers, ConstraintValidatorContext constraintValidatorContext) {
        for (String phoneNumber: phoneNumbers) {
            if (phoneNumber == null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Phone number shouldn't be empty or blank!")
                        .addPropertyNode("phone_number").addConstraintViolation();
                return true;
            } else {
                if (!phoneNumberRegx.matcher(phoneNumber).matches()) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Phone number should be valid!")
                            .addPropertyNode("phone_number").addConstraintViolation();
                    return true;
                }
            }
        }
        return false;
    }
}
