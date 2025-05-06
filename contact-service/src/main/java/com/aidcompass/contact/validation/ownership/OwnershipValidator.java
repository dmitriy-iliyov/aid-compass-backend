package com.aidcompass.contact.validation.ownership;

import com.aidcompass.contact.models.dto.SystemContactDto;

import java.util.List;

public interface OwnershipValidator {
    void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList);
}
