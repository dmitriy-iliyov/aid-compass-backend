package com.example.contact.validation.ownership;

import com.example.contact.models.dto.SystemContactDto;

import java.util.List;

public interface OwnershipValidator {

    void assertOwnership(Long id, List<SystemContactDto> systemContactDtoList);

    void assertOwnership(List<Long> contactIds, List<SystemContactDto> systemContactDtoList);
}
