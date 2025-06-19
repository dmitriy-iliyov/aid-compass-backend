package com.aidcompass.contact.validation.validators;

import com.aidcompass.base_dto.SystemContactDto;

import java.util.List;

public interface ContactOwnershipValidator {

    void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList);
}
