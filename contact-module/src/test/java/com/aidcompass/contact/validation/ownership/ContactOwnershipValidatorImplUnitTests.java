//package com.aidcompass.contact.validation.ownership;
//
//
//import com.aidcompass.dto.system.SystemContactDto;
//import com.aidcompass.contact.validation.validators.impl.ContactOwnershipValidatorImpl;
//import com.aidcompass.enums.ContactType;
//import com.aidcompass.exceptions.invalid_input.OwnerShipException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//
//class ContactOwnershipValidatorImplUnitTests {
//
//    private final ContactOwnershipValidatorImpl validator = new ContactOwnershipValidatorImpl();
//
//    private SystemContactDto createContactDto(Long id) {
//        return new SystemContactDto(
//                id,
//                UUID.randomUUID(),
//                ContactType.EMAIL,
//                "test@example.com",
//                false,
//                true,
//                false
//        );
//    }
//
//    @Test
//    @DisplayName("UT assertOwnership(List) should pass when all IDs exist in list")
//    void assertOwnershipList_shouldPass_whenAllIdsExist() {
//        List<SystemContactDto> contacts = List.of(
//                createContactDto(10L),
//                createContactDto(20L),
//                createContactDto(30L)
//        );
//
//        List<Long> idsToCheck = List.of(10L, 30L);
//
//        assertDoesNotThrow(() -> validator.assertOwnership(idsToCheck, contacts));
//    }
//
//    @Test
//    @DisplayName("UT assertOwnership(List) should throw OwnerShipException when one ID is missing")
//    void assertOwnershipList_shouldThrow_whenOneIdMissing() {
//        List<SystemContactDto> contacts = List.of(
//                createContactDto(100L),
//                createContactDto(200L)
//        );
//
//        List<Long> idsToCheck = List.of(100L, 300L);
//
//        assertThatThrownBy(() -> validator.assertOwnership(idsToCheck, contacts))
//                .isInstanceOf(OwnerShipException.class);
//    }
//}
//
