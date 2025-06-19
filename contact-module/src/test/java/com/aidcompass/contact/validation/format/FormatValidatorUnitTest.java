//package com.aidcompass.contact.validation.format;
//
//import com.aidcompass.contact.validation.validators.impl.FormatValidatorImpl;
//import com.aidcompass.enums.ContactType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(MockitoExtension.class)
//public class FormatValidatorUnitTest {
//
//    @InjectMocks
//    FormatValidatorImpl formatValidator;
//
//
//    @Test
//    @DisplayName("UT isEmailValid() should return true for valid email format")
//    void isEmailValid_whenValidEmail_shouldReturnTrue() {
//        String validEmail = "test@example.com";
//
//        boolean result = formatValidator.isEmailValid(validEmail);
//
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("UT isEmailValid() should return false for invalid email format")
//    void isEmailValid_whenInvalidEmail_shouldReturnFalse() {
//        String[] invalidEmails = {
//                "testexample.com", // missing @
//                "test@", // missing domain
//                "test@example", // missing TLD
//                "@example.com", // missing local part
//                "te st@example.com" // space in local part
//        };
//
//        for (String invalidEmail : invalidEmails) {
//            boolean result = formatValidator.isEmailValid(invalidEmail);
//            assertFalse(result, "Should be false for: " + invalidEmail);
//        }
//    }
//
//    @Test
//    @DisplayName("UT isLengthValid() should return true for valid email length")
//    void isLengthValid_whenValidEmailLength_shouldReturnTrue() {
//        String validLengthEmail = "test@example.com";
//
//        boolean result = formatValidator.isLengthValid(ContactType.EMAIL, validLengthEmail);
//
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("UT isLengthValid() should return false for email too short")
//    void isLengthValid_whenEmailTooShort_shouldReturnFalse() {
//        String shortEmail = "a@b.co";
//
//        boolean result = formatValidator.isLengthValid(ContactType.EMAIL, shortEmail);
//
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("UT isLengthValid() should return false for email too long")
//    void isLengthValid_whenEmailTooLong_shouldReturnFalse() {
//        String longEmail = "verylongusername123456789012345678901234567890@example.com";
//
//        boolean result = formatValidator.isLengthValid(ContactType.EMAIL, longEmail);
//
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("UT isLengthValid() should return false for non-email type")
//    void isLengthValid_whenNonEmailType_shouldReturnFalse() {
//        String phoneNumber = "+123456789012";
//
//        boolean result = formatValidator.isLengthValid(ContactType.PHONE_NUMBER, phoneNumber);
//
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("UT isPhoneNumberValid() should return true for valid phone number format")
//    void isPhoneNumberValid_whenValidPhoneNumber_shouldReturnTrue() {
//        String validPhoneNumber = "+123456789012";
//
//        boolean result = formatValidator.isPhoneNumberValid(validPhoneNumber);
//
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("UT isPhoneNumberValid() should return false for invalid phone number format")
//    void isPhoneNumberValid_whenInvalidPhoneNumber_shouldReturnFalse() {
//        String[] invalidPhoneNumbers = {
//                "123456789012", // missing +
//                "+12345678901", // too short
//                "+1234567890123", // too long
//                "+1234-56789012", // contains hyphen
//                "+12345 6789012" // contains space
//        };
//
//        for (String invalidPhoneNumber : invalidPhoneNumbers) {
//            boolean result = formatValidator.isPhoneNumberValid(invalidPhoneNumber);
//            assertFalse(result, "Should be false for: " + invalidPhoneNumber);
//        }
//    }
//}
