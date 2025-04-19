package com.example.contacts.validation.update;

import com.example.contacts.models.ContactType;
import com.example.contacts.models.ContactsDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ContactsUpdateValidator implements ConstraintValidator<ContactsUpdate, ContactsDto> {

    @Value("{}")
    private short maxEmailCount;

    @Value("{}")
    private short maxPhoneNumberCount;


    @Override
    public boolean isValid(ContactsDto contactsDto, ConstraintValidatorContext constraintValidatorContext) {

        boolean hasErrors = false;
        Map<String, List<String>> contacts = contactsDto.contacts();

        if (contacts.get(ContactType.EMAIL.toString()).size() > 2) {
            hasErrors = true;
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("You can't put more than " + maxEmailCount + "emails")
                    .addPropertyNode("emails")
                    .addConstraintViolation();
        }

        if (contacts.get(ContactType.PHONE_NUMBER.toString()).size() > 2) {
            hasErrors = true;
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("You can't put more than " + maxPhoneNumberCount + "phone numbers")
                    .addPropertyNode("phone_numbers")
                    .addConstraintViolation();
        }

        return !hasErrors;
    }
}
