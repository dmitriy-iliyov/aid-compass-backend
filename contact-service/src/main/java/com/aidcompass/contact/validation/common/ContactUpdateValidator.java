package com.aidcompass.contact.validation.common;

import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact.validation.contact.ContactValidator;
import com.aidcompass.contact_type.models.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactUpdateValidator implements ConstraintValidator<Contact, ContactUpdateDto> {

    private final ContactValidator validator;


    @Override
    public boolean isValid(ContactUpdateDto contactUpdateDto, ConstraintValidatorContext context) {

        boolean hasErrors = false;

        if (contactUpdateDto == null) {
            context.buildConstraintViolationWithTemplate("Contact can't be empty or blank!")
                    .addPropertyNode("contact")
                    .addConstraintViolation();
            return false;
        }

        String contact = contactUpdateDto.contact();
        ContactType type = contactUpdateDto.type();
        if(type == ContactType.EMAIL) {

            if (!validator.isEmailValid(contact)) {
                context.buildConstraintViolationWithTemplate("Email should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!validator.isLengthValid(type, contact)) {
                context.buildConstraintViolationWithTemplate("Email length must be greater than 7 and less than 50!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            return !hasErrors;
        } else if (type == ContactType.PHONE_NUMBER) {
            if (!validator.isPhoneNumberValid(contact)) {
                context.buildConstraintViolationWithTemplate("Phone number should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }
            return !hasErrors;
        } else {
            context.buildConstraintViolationWithTemplate("Contact type is invalid!")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            return false;
        }
    }
}
