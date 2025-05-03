package com.aidcompass.contact.validation.ownership;

import com.aidcompass.contact.models.dto.SystemContactDto;

import java.util.List;

public interface OwnershipValidator {

    void assertOwnership(Long id, List<SystemContactDto> systemContactDtoList);

    void assertOwnership(List<Long> contactIds, List<SystemContactDto> systemContactDtoList);
}
