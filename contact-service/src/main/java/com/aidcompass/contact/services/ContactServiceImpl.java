package com.aidcompass.contact.services;

import com.aidcompass.contact.ContactRepository;
import com.aidcompass.contact.mapper.ContactMapper;
import com.aidcompass.contact.models.ContactEntity;
import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.exceptions.not_found.ContactNotFoundByContactException;
import com.aidcompass.exceptions.not_found.ContactNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// метод для установки контакта привязки который по рестовому обращяеться с PUT запросом
// (чтоб можно было и новый установить одноврименно с добавлением и старый просто пометив флагом)
// проверить масштабируемость
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService, SystemContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;


    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto) {
        ContactEntity contactEntity = contactMapper.toEntity(dto);
        contactEntity.setOwnerId(ownerId);
        return contactMapper.toPrivateResponseDto(contactRepository.save(contactEntity));
    }

    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList) {
        List<ContactEntity> contactEntityList = contactMapper.toEntityList(dtoList);
        contactEntityList.forEach(contactEntity -> contactEntity.setOwnerId(ownerId));
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(contactEntityList));
    }

    @Transactional
    @Override
    public void markContactAsLinked(UUID ownerId, Long contactId) {
        ContactEntity linkedContactEntity = contactRepository.findByOwnerIdAndLinkedToAccount(ownerId, true)
                .orElse(null);

        if (linkedContactEntity != null) {
            linkedContactEntity.setLinkedToAccount(false);
            contactRepository.save(linkedContactEntity);
        }

        ContactEntity contactEntity = contactRepository.findById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        contactEntity.setLinkedToAccount(true);

        contactRepository.save(contactEntity);
    }

    @Cacheable(value = "exists", key="#typeEntity.type.toString() + ':' + #contact", condition = "#result == true")
    @Transactional(readOnly = true)
    @Override
    public boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact) {
        return contactRepository.existsByTypeEntityAndContact(typeEntity, contact);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByOwnerIdAndContactType(UUID ownerId, ContactType type) {
        return contactRepository.countByOwnerIdAndContentType(ownerId, type);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerId(ownerId);
        return contactMapper.toPrivateResponseDtoList(contactEntityList);
    }

    @Cacheable(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerIdAndIsConfirmed(ownerId, true);
        return contactMapper.toPublicResponseDtoList(contactEntityList);
    }

    @Cacheable(value = "primary_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerIdAndIsPrimary(ownerId, true);
        return contactMapper.toPublicResponseDtoList(contactEntityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerIdAndIsPrimary(ownerId, false);
        return contactMapper.toPublicResponseDtoList(contactEntityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemContactDto> findAllByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerId(ownerId);
        return contactMapper.toSystemDtoList(contactEntityList);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findByContact(String contact) {
        ContactEntity contactEntity = contactRepository.findByContact(contact)
                .orElseThrow(ContactNotFoundByContactException::new);
        return contactMapper.toSystemDto(contactEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findById(Long contactId) {
        ContactEntity entity = contactRepository.findById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        return contactMapper.toSystemDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isContactConfirmed(Long contactId) {
        return contactRepository.isContactConfirmed(contactId);
    }

    @Transactional
    @Override
    public void confirmContactById(Long contactId) {
        if (contactRepository.confirmContactById(contactId) == 0) {
            throw new ContactNotFoundByIdException();
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()", condition = "#result.confirmed == true"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()", condition = "#result.primary == true ")
    })
    @Transactional
    @Override
    public PrivateContactResponseDto updateById(UUID ownerId, ContactUpdateDto dto) {
        ContactEntity contactEntity = contactRepository.findById(dto.id()).orElseThrow(
                ContactNotFoundByIdException::new
        );
        contactMapper.updateEntityFromDto(dto, contactEntity);
        return contactMapper.toPrivateResponseDto(contactRepository.save(contactEntity));
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList) {
        Map<Long, ContactUpdateDto> contactIdDtoMap = dtoList.stream()
                .collect(Collectors.toMap(ContactUpdateDto::id, Function.identity()));
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerId(ownerId);
        for (ContactEntity contactEntity: contactEntityList) {
            ContactUpdateDto contactUpdateDto = contactIdDtoMap.get(contactEntity.getId());
            contactMapper.updateEntityFromDto(contactUpdateDto, contactEntity);
        }
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(contactEntityList));
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public void deleteById(UUID ownerId, Long contactId){
        contactRepository.deleteById(contactId);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public void deleteAll(UUID ownerId) {
        contactRepository.deleteAllByOwnerId(ownerId);
    }
}
