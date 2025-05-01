package com.example.contact.services;

import com.example.contact.models.dto.ContactCreateDto;
import com.example.contact.models.dto.ContactUpdateDto;
import com.example.contact.models.dto.PrivateContactResponseDto;
import com.example.contact.models.dto.PublicContactResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    PrivateContactResponseDto save(UUID ownerId, ContactCreateDto contactCreateDto);

    List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contactCreateDtoList);

    void markContactAsLinked(UUID ownerId, Long id);

    List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId);

    PrivateContactResponseDto updateById(ContactUpdateDto contactUpdateDto);

    List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList);

    void deleteById(Long id);

    void deleteAll(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);
}
