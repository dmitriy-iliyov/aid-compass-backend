package com.aidcompass.contact.validation.ownership;


import com.aidcompass.contact.models.dto.SystemContactDto;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.exceptions.invalid_input.OwnerShipException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OwnershipValidatorImplUnitTests {

    private final OwnershipValidatorImpl validator = new OwnershipValidatorImpl();

    private SystemContactDto createContactDto(Long id) {
        return new SystemContactDto(
                id,
                UUID.randomUUID(),
                ContactType.EMAIL,
                "test@example.com",
                false,
                true,
                false
        );
    }

    @Test
    @DisplayName("UT assertOwnership(id) should pass when ID exists in list")
    void assertOwnership_shouldPass_whenIdExists() {
        List<SystemContactDto> contacts = List.of(
                createContactDto(1L),
                createContactDto(2L)
        );

        assertDoesNotThrow(() -> validator.assertOwnership(1L, contacts));
    }

    @Test
    @DisplayName("UT assertOwnership(id) should throw OwnerShipException when ID not in list")
    void assertOwnership_shouldThrow_whenIdMissing() {
        List<SystemContactDto> contacts = List.of(
                createContactDto(2L),
                createContactDto(3L)
        );

        assertThatThrownBy(() -> validator.assertOwnership(1L, contacts))
                .isInstanceOf(OwnerShipException.class);
    }

    @Test
    @DisplayName("UT assertOwnership(List) should pass when all IDs exist in list")
    void assertOwnershipList_shouldPass_whenAllIdsExist() {
        List<SystemContactDto> contacts = List.of(
                createContactDto(10L),
                createContactDto(20L),
                createContactDto(30L)
        );

        List<Long> idsToCheck = List.of(10L, 30L);

        assertDoesNotThrow(() -> validator.assertOwnership(idsToCheck, contacts));
    }

    @Test
    @DisplayName("UT assertOwnership(List) should throw OwnerShipException when one ID is missing")
    void assertOwnershipList_shouldThrow_whenOneIdMissing() {
        List<SystemContactDto> contacts = List.of(
                createContactDto(100L),
                createContactDto(200L)
        );

        List<Long> idsToCheck = List.of(100L, 300L);

        assertThatThrownBy(() -> validator.assertOwnership(idsToCheck, contacts))
                .isInstanceOf(OwnerShipException.class);
    }
}

