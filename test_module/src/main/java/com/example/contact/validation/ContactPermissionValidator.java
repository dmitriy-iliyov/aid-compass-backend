package com.example.contact.validation;

import com.example.contact.models.dto.ContactUpdateDto;
import com.example.global_exceptions.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface ContactPermissionValidator {
    List<ErrorDto> isUpdatePermit(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList);

    List<ErrorDto> isUpdatePermit(UUID ownerId, ContactUpdateDto contactUpdateDto);

    List<ErrorDto> isDeletePermit(UUID ownerId, Long id);

    List<ErrorDto> isLinkingPermit(UUID ownerId, Long id);
}
