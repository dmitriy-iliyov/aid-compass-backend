package com.aidcompass.contact.services;

import com.aidcompass.contact.facades.ContactChangingListener;
import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import com.aidcompass.enums.Authority;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto);

    List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList);

    void markContactAsLinked(UUID ownerId, Long id);

    List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);

    PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto dto, ContactChangingListener callback);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList, ContactChangingListener callback);

    void deleteById(UUID ownerId, Long id);

    void deleteAll(UUID ownerId);
}
