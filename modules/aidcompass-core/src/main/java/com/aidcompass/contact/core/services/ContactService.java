package com.aidcompass.contact.core.services;

import com.aidcompass.contact.core.facades.ContactChangingListener;
import com.aidcompass.contact.core.models.dto.ContactCreateDto;
import com.aidcompass.contact.core.models.dto.ContactUpdateDto;
import com.aidcompass.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ContactService {

    PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto);

    List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList);

    List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);

    List<SystemContactDto> findAllPrimaryByOwnerIdIn(Set<UUID> ids);

    void markContactAsLinked(UUID ownerId, Long id);

    PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto dto, ContactChangingListener callback);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList, ContactChangingListener callback);

    void deleteById(UUID ownerId, Long id);
    void deleteAll(UUID ownerId);
}
