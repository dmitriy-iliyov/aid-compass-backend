//package com.aidcompass.contact_type.models;
//
//import com.aidcompass.enums.ContactType;
//import com.aidcompass.exceptions.invalid_input.InvalidContactTypeException;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ContactTypeUnitTests {
//
//    @Test
//    @DisplayName("UT toString() should return lower case string for each ContactType")
//    void testToString_shouldReturnLowerCaseString() {
//        assertEquals("email", ContactType.EMAIL.toString());
//        assertEquals("phone_number", ContactType.PHONE_NUMBER.toString());
//    }
//
//    @Test
//    @DisplayName("UT toContactType() should return ContactType when valid string is provided")
//    void testToContactType_shouldReturnContactType_whenValidString() {
//        assertEquals(ContactType.EMAIL, ContactType.toContactType("email"));
//        assertEquals(ContactType.PHONE_NUMBER, ContactType.toContactType("phone_number"));
//    }
//
////    @Test
////    @DisplayName("UT toContactType() should throw InvalidContactTypeException when invalid string is provided")
////    void testToContactType_shouldThrowException_whenInvalidString() {
////        assertThrows(InvalidContactTypeException.class, () -> ContactType.toContactType("invalid_type"));
////    }
//}
//
