package com.aidcompass.contact.validation.validators;

import com.aidcompass.contact.models.dto.system.SystemContactDto;

import java.util.List;

public interface OwnershipValidator {

    void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList);
}
