package com.aidcompass.contact.validation.validators;

import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.markers.UpdateDto;
import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.models.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface PermissionValidator {
    List<ErrorDto> isUpdatePermit(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList);

    List<ErrorDto> isUpdatePermit(UUID ownerId, UpdateDto contactUpdateDto);

    List<ErrorDto> isDeletePermit(UUID ownerId, Long id);

    List<ErrorDto> isLinkingPermit(UUID ownerId, Long id);

    SystemContactDto isConfirmPermit(UUID ownerId, Long contactId);
}
