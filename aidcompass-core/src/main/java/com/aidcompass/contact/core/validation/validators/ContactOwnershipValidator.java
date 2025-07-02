package com.aidcompass.contact.core.validation.validators;

import com.aidcompass.contact.core.models.dto.system.SystemContactDto;

import java.util.List;

public interface ContactOwnershipValidator {

    void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList);
}
