package com.aidcompass.contact.validation.contact;

import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.validators.impl.UniquenessValidatorImpl;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.exceptions.not_found.ContactNotFoundByContactException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniquenessValidatorImplUnitTests {

    @Mock
    SystemContactService systemContactFacade;

    @InjectMocks
    UniquenessValidatorImpl uniquenessValidator;


    @Test
    @DisplayName("UT isEmailValid() should return true for valid email format")
    void isEmailValid_whenValidEmail_shouldReturnTrue() {
        String validEmail = "test@example.com";

        boolean result = uniquenessValidator.isEmailValid(validEmail);

        assertTrue(result);
    }

    @Test
    @DisplayName("UT isEmailValid() should return false for invalid email format")
    void isEmailValid_whenInvalidEmail_shouldReturnFalse() {
        String[] invalidEmails = {
                "testexample.com", // missing @
                "test@", // missing domain
                "test@example", // missing TLD
                "@example.com", // missing local part
                "te st@example.com" // space in local part
        };

        for (String invalidEmail : invalidEmails) {
            boolean result = uniquenessValidator.isEmailValid(invalidEmail);
            assertFalse(result, "Should be false for: " + invalidEmail);
        }
    }

    @Test
    @DisplayName("UT isEmailUnique() should return true when email doesn't exist")
    void isEmailUnique_whenEmailDoesntExist_shouldReturnTrue() {
        String email = "unique@example.com";
        ContactTypeEntity typeEntity = new ContactTypeEntity(1, ContactType.EMAIL);

        when(systemContactFacade.existsByTypeEntityAndContact(typeEntity, email)).thenReturn(false);

        boolean result = uniquenessValidator.isEmailUnique(email);

        assertTrue(result);
        verify(systemContactFacade, times(1)).existsByTypeEntityAndContact(typeEntity, email);
    }

    @Test
    @DisplayName("UT isEmailUnique() should return false when email exists")
    void isEmailUnique_whenEmailExists_shouldReturnFalse() {
        String email = "existing@example.com";
        ContactTypeEntity typeEntity = new ContactTypeEntity(1, ContactType.EMAIL);

        when(systemContactFacade.existsByTypeEntityAndContact(typeEntity, email)).thenReturn(true);

        boolean result = uniquenessValidator.isEmailUnique(email);

        assertFalse(result);
        verify(systemContactFacade, times(1)).existsByTypeEntityAndContact(typeEntity, email);
    }

    @Test
    @DisplayName("UT isLengthValid() should return true for valid email length")
    void isLengthValid_whenValidEmailLength_shouldReturnTrue() {
        String validLengthEmail = "test@example.com";

        boolean result = uniquenessValidator.isLengthValid(ContactType.EMAIL, validLengthEmail);

        assertTrue(result);
    }

    @Test
    @DisplayName("UT isLengthValid() should return false for email too short")
    void isLengthValid_whenEmailTooShort_shouldReturnFalse() {
        String shortEmail = "a@b.co";

        boolean result = uniquenessValidator.isLengthValid(ContactType.EMAIL, shortEmail);

        assertFalse(result);
    }

    @Test
    @DisplayName("UT isLengthValid() should return false for email too long")
    void isLengthValid_whenEmailTooLong_shouldReturnFalse() {
        String longEmail = "verylongusername123456789012345678901234567890@example.com";

        boolean result = uniquenessValidator.isLengthValid(ContactType.EMAIL, longEmail);

        assertFalse(result);
    }

    @Test
    @DisplayName("UT isLengthValid() should return false for non-email type")
    void isLengthValid_whenNonEmailType_shouldReturnFalse() {
        String phoneNumber = "+123456789012";

        boolean result = uniquenessValidator.isLengthValid(ContactType.PHONE_NUMBER, phoneNumber);

        assertFalse(result);
    }

    @Test
    @DisplayName("UT isPhoneNumberValid() should return true for valid phone number format")
    void isPhoneNumberValid_whenValidPhoneNumber_shouldReturnTrue() {
        String validPhoneNumber = "+123456789012";

        boolean result = uniquenessValidator.isPhoneNumberValid(validPhoneNumber);

        assertTrue(result);
    }

    @Test
    @DisplayName("UT isPhoneNumberValid() should return false for invalid phone number format")
    void isPhoneNumberValid_whenInvalidPhoneNumber_shouldReturnFalse() {
        String[] invalidPhoneNumbers = {
                "123456789012", // missing +
                "+12345678901", // too short
                "+1234567890123", // too long
                "+1234-56789012", // contains hyphen
                "+12345 6789012" // contains space
        };

        for (String invalidPhoneNumber : invalidPhoneNumbers) {
            boolean result = uniquenessValidator.isPhoneNumberValid(invalidPhoneNumber);
            assertFalse(result, "Should be false for: " + invalidPhoneNumber);
        }
    }

    @Test
    @DisplayName("UT isEmailUnique() with ownerId should return true when email belongs to owner")
    void isEmailUnique_whenEmailBelongsToOwner_shouldReturnTrue() {
        String email = "owner@example.com";
        UUID ownerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(email))
                .thenReturn(
                        new SystemContactDto(1L, ownerId, ContactType.EMAIL, email,
                                false, false, false)
                );

        boolean result = uniquenessValidator.isEmailUnique(email, ownerId);

        assertTrue(result);
        verify(systemContactFacade, times(1)).findByContact(email);
    }

    @Test
    @DisplayName("UT isEmailUnique() with ownerId should return false when email belongs to different owner")
    void isEmailUnique_whenEmailBelongsToDifferentOwner_shouldReturnFalse() {
        String email = "other@example.com";
        UUID ownerId = UUID.randomUUID();
        UUID differentOwnerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(email))
                .thenReturn(
                        new SystemContactDto(1L, differentOwnerId, ContactType.EMAIL, email,
                                false, false, false)
                );

        boolean result = uniquenessValidator.isEmailUnique(email, ownerId);

        assertFalse(result);
        verify(systemContactFacade, times(1)).findByContact(email);
    }

    @Test
    @DisplayName("UT isEmailUnique() with ownerId should return true when email not found")
    void isEmailUnique_whenEmailNotFound_shouldReturnTrue() {
        String email = "notfound@example.com";
        UUID ownerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(email)).thenThrow(new ContactNotFoundByContactException());

        boolean result = uniquenessValidator.isEmailUnique(email, ownerId);

        assertTrue(result);
        verify(systemContactFacade, times(1)).findByContact(email);
    }

    @Test
    @DisplayName("UT isPhoneNumberUnique() should return true when phone number doesn't exist")
    void isPhoneNumberUnique_whenPhoneNumberDoesntExist_shouldReturnTrue() {
        String phoneNumber = "+123456789012";
        ContactTypeEntity typeEntity = new ContactTypeEntity(1, ContactType.EMAIL);

        when(systemContactFacade.existsByTypeEntityAndContact(typeEntity, phoneNumber)).thenReturn(false);

        boolean result = uniquenessValidator.isPhoneNumberUnique(phoneNumber);

        assertTrue(result);
        verify(systemContactFacade, times(1))
                .existsByTypeEntityAndContact(typeEntity, phoneNumber);
    }

    @Test
    @DisplayName("UT isPhoneNumberUnique() should return false when phone number exists")
    void isPhoneNumberUnique_whenPhoneNumberExists_shouldReturnFalse() {
        String phoneNumber = "+123456789012";
        ContactTypeEntity typeEntity = new ContactTypeEntity(1, ContactType.EMAIL);

        when(systemContactFacade.existsByTypeEntityAndContact(typeEntity, phoneNumber)).thenReturn(true);

        boolean result = uniquenessValidator.isPhoneNumberUnique(phoneNumber);

        assertFalse(result);
        verify(systemContactFacade, times(1))
                .existsByTypeEntityAndContact(typeEntity, phoneNumber);
    }

    @Test
    @DisplayName("UT isPhoneNumberUnique() with ownerId should return true when phone number belongs to owner")
    void isPhoneNumberUnique_whenPhoneNumberBelongsToOwner_shouldReturnTrue() {
        String phoneNumber = "+123456789012";
        UUID ownerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(phoneNumber))
                .thenReturn(
                        new SystemContactDto(1L, ownerId, ContactType.PHONE_NUMBER, phoneNumber,
                                false, false, false)
                );

        boolean result = uniquenessValidator.isPhoneNumberUnique(phoneNumber, ownerId);

        assertTrue(result);
        verify(systemContactFacade, times(1)).findByContact(phoneNumber);
    }

    @Test
    @DisplayName("UT isPhoneNumberUnique() with ownerId should return false when phone number belongs to different owner")
    void isPhoneNumberUnique_whenPhoneNumberBelongsToDifferentOwner_shouldReturnFalse() {
        String phoneNumber = "+123456789012";
        UUID ownerId = UUID.randomUUID();
        UUID differentOwnerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(phoneNumber))
                .thenReturn(
                        new SystemContactDto(1L, differentOwnerId, ContactType.PHONE_NUMBER, phoneNumber,
                                false, false, false)
                );

        boolean result = uniquenessValidator.isPhoneNumberUnique(phoneNumber, ownerId);

        assertFalse(result);
        verify(systemContactFacade, times(1)).findByContact(phoneNumber);
    }

    @Test
    @DisplayName("UT isPhoneNumberUnique() with ownerId should return true when phone number not found")
    void isPhoneNumberUnique_whenPhoneNumberNotFound_shouldReturnTrue() {
        String phoneNumber = "+123456789012";
        UUID ownerId = UUID.randomUUID();

        when(systemContactFacade.findByContact(phoneNumber)).thenThrow(new ContactNotFoundByContactException());

        boolean result = uniquenessValidator.isPhoneNumberUnique(phoneNumber, ownerId);

        assertTrue(result);
        verify(systemContactFacade, times(1)).findByContact(phoneNumber);
    }

    @Test
    @DisplayName("UT checkUniquesForType() should add error when email is not unique")
    void checkUniquesForType_whenEmailNotUnique_shouldAddError() {
        UUID ownerId = UUID.randomUUID();
        ContactUpdateDto updateDto = new ContactUpdateDto(1L, ContactType.EMAIL, "test@example.com", true);
        List<ErrorDto> errors = new ArrayList<>();

        when(systemContactFacade.findByContact(updateDto.contact()))
                .thenReturn(
                        new SystemContactDto(1L, UUID.randomUUID(), ContactType.EMAIL, updateDto.contact(),
                                false, false, false)
                );

        uniquenessValidator.checkUniquesForType(ownerId, updateDto, errors);

        assertEquals(1, errors.size());
        assertEquals("email", errors.get(0).field());
        assertEquals("Email is in use!", errors.get(0).message());
    }

    @Test
    @DisplayName("UT checkUniquesForType() should not add error when email is unique")
    void checkUniquesForType_whenEmailUnique_shouldNotAddError() {
        UUID ownerId = UUID.randomUUID();
        ContactUpdateDto updateDto = new ContactUpdateDto(1L, ContactType.EMAIL, "test@example.com", true);
        List<ErrorDto> errors = new ArrayList<>();

        when(systemContactFacade.findByContact(updateDto.contact()))
                .thenReturn(
                        new SystemContactDto(1L,ownerId, ContactType.EMAIL, updateDto.contact(),
                                false, false, false)
                );

        uniquenessValidator.checkUniquesForType(ownerId, updateDto, errors);

        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("UT checkUniquesForType() should add error when phone number is not unique")
    void checkUniquesForType_whenPhoneNumberNotUnique_shouldAddError() {
        UUID ownerId = UUID.randomUUID();
        ContactUpdateDto updateDto = new ContactUpdateDto(1L, ContactType.PHONE_NUMBER, "+123456789012", true);
        List<ErrorDto> errors = new ArrayList<>();

        when(systemContactFacade.findByContact(updateDto.contact()))
                .thenReturn(
                        new SystemContactDto(1L, UUID.randomUUID(), ContactType.PHONE_NUMBER, updateDto.contact(),
                                false, false, false)
                );

        uniquenessValidator.checkUniquesForType(ownerId, updateDto, errors);

        assertEquals(1, errors.size());
        assertEquals("phone_number", errors.get(0).field());
        assertEquals("Phone number is in use!", errors.get(0).message());
    }

    @Test
    @DisplayName("UT checkUniquesForType() should not add error when phone number is unique")
    void checkUniquesForType_whenPhoneNumberUnique_shouldNotAddError() {
        UUID ownerId = UUID.randomUUID();
        ContactUpdateDto updateDto = new ContactUpdateDto(1L, ContactType.PHONE_NUMBER, "+123456789012", true);
        List<ErrorDto> errors = new ArrayList<>();

        when(systemContactFacade.findByContact(updateDto.contact()))
                .thenReturn(
                        new SystemContactDto(
                                1L,
                                ownerId,
                                ContactType.PHONE_NUMBER,
                                updateDto.contact(),
                                false,
                                false,
                                false
                        )
                );

        uniquenessValidator.checkUniquesForType(ownerId, updateDto, errors);

        assertTrue(errors.isEmpty());
    }
}
