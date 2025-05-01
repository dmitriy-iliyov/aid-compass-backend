package com.example.contact.validation.common;

import com.example.contact.models.dto.ContactCreateDto;
import com.example.contact.validation.contact.ContactValidator;
import com.example.contact_type.models.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ContactCreateValidator implements ConstraintValidator<Contact, ContactCreateDto> {

    private final ContactValidator validator;


    @Override
    public boolean isValid(ContactCreateDto contact, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        if (contact.type() == ContactType.EMAIL) {
            if (!validator.isEmailValid(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!validator.isLengthValid(ContactType.EMAIL, contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email length must be greater than 7 and less than 50!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!validator.isEmailUnique(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email is in use!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

        } else if (contact.type() == ContactType.PHONE_NUMBER) {
            if (!validator.isPhoneNumberValid(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Phone number should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!validator.isPhoneNumberUnique(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Phone number is in use!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

        } else {
            context.buildConstraintViolationWithTemplate("Contact type is invalid!")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            hasErrors = true;
        }

        return !hasErrors;
    }
}
