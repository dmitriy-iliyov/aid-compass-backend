package com.aidcompass.contact.facades;

import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.dto.PrivateContactResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;

import java.util.List;
import java.util.UUID;

public interface GeneralContactOrchestrator {

    List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contacts);

    void markEmailAsLinkedToAccount(UUID ownerId, Long id);

    List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);

    PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto contact);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contacts);

    void delete(UUID ownerId, Long id);

    void deleteAll(UUID ownerId);

    PrivateContactResponseDto save(UUID ownerId, ContactCreateDto contact);

    void requestConfirmation(UUID ownerId, Long contactId);
}
