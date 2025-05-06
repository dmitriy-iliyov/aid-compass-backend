package com.aidcompass.contact.services;

import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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

    PrivateContactResponseDto updateById(UUID ownerId, ContactUpdateDto dto);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList);

    void deleteById(UUID ownerId, Long id);

    void deleteAll(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);
}
