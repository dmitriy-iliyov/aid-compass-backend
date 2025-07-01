package com.aidcompass.contact.services;

import com.aidcompass.contact.facades.ContactChangingListener;
import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.dto.PrivateContactResponseDto;
import com.aidcompass.contracts.ContactDeleteService;
import com.aidcompass.contracts.ContactReadService;

import java.util.List;
import java.util.UUID;

public interface ContactService extends ContactReadService, ContactDeleteService {

    PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto);

    List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList);

    void markContactAsLinked(UUID ownerId, Long id);

    PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto dto, ContactChangingListener callback);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList, ContactChangingListener callback);
}
