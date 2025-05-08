package com.aidcompass.contact.services;

import com.aidcompass.contact.facades.ContactChangingListener;
import com.aidcompass.contact.repositories.ContactRepository;
import com.aidcompass.contact.mappers.ContactMapper;
import com.aidcompass.contact.models.entity.ContactEntity;
import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.models.dto.system.SystemContactUpdateDto;
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
public class UnifiedContactService implements ContactService, SystemContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;


    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto) {
        ContactEntity entity = contactMapper.toEntity(dto);
        entity.setOwnerId(ownerId);
        return contactMapper.toPrivateResponseDto(contactRepository.save(entity));
    }

    @CacheEvict(value = "public_contacts", key = "#dto.ownerId.toString()")
    @Transactional
    @Override
    public SystemContactDto save(SystemContactDto dto) {
        ContactEntity entity = contactRepository.save(contactMapper.toEntity(dto));
        return contactMapper.toSystemDto(entity);
    }

    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList) {
        List<ContactEntity> entityList = contactMapper.toEntityList(dtoList);
        entityList.forEach(contactEntity -> contactEntity.setOwnerId(ownerId));
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(entityList));
    }

    @Transactional
    @Override
    public void confirmContactById(Long contactId) {
        if (contactRepository.confirmContactById(contactId) == 0) {
            throw new ContactNotFoundByIdException();
        }
    }

    @Transactional
    @Override
    public void markContactAsLinked(UUID ownerId, Long contactId) {
        ContactEntity linkedEntity = contactRepository.findByOwnerIdAndLinkedToAccount(ownerId, true)
                .orElse(null);

        if (linkedEntity != null) {
            linkedEntity.setLinkedToAccount(false);
            contactRepository.save(linkedEntity);
        }

        ContactEntity newLinkedEntity = contactRepository.findById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        newLinkedEntity.setLinkedToAccount(true);

        contactRepository.save(newLinkedEntity);
    }

    @Cacheable(value = "exists", key="#typeEntity.type.toString() + ':' + #contact", condition = "#result == true")
    @Transactional(readOnly = true)
    @Override
    public boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact) {
        return contactRepository.existsByTypeEntityAndContact(typeEntity, contact);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isContactConfirmed(Long contactId) {
        return contactRepository.isContactConfirmed(contactId);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByOwnerIdAndContactType(UUID ownerId, ContactType type) {
        return contactRepository.countByOwnerIdAndContentType(ownerId, type);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = contactRepository.findByOwnerId(ownerId);
        return contactMapper.toPrivateResponseDtoList(entityList);
    }

    @Cacheable(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = contactRepository.findByOwnerIdAndIsConfirmed(ownerId, true);
        return contactMapper.toPublicResponseDtoList(entityList);
    }

    @Cacheable(value = "primary_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = contactRepository.findByOwnerIdAndIsPrimary(ownerId, true);
        return contactMapper.toPublicResponseDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = contactRepository.findByOwnerIdAndIsPrimary(ownerId, false);
        return contactMapper.toPublicResponseDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemContactDto> findAllByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = contactRepository.findByOwnerId(ownerId);
        return contactMapper.toSystemDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findByContact(String contact) {
        ContactEntity entity = contactRepository.findByContact(contact)
                .orElseThrow(ContactNotFoundByContactException::new);
        return contactMapper.toSystemDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findById(Long contactId) {
        ContactEntity entity = contactRepository.findWithTypeById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        return contactMapper.toSystemDto(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()", condition = "#result.confirmed == true"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()", condition = "#result.primary == true ")
    })
    @Transactional
    public PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto dto, ContactChangingListener listener) {
        ContactEntity entity = contactRepository.findWithTypeById(dto.id()).orElseThrow(
                ContactNotFoundByIdException::new
        );

        boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
        contactMapper.updateEntityFromDto(dto, entity, isConfirmed);
        entity = contactRepository.save(entity);

        if (!isConfirmed) {
            listener.callback(dto);
        }

        return contactMapper.toPrivateResponseDto(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#dto.ownerId.toString()", condition = "#result.confirmed == true"),
            @CacheEvict(value = "primary_contacts", key = "#dto.ownerId.toString()", condition = "#result.primary == true ")
    })
    @Transactional
    @Override
    public SystemContactDto update(SystemContactUpdateDto dto) {
        ContactEntity entity = contactRepository.findWithTypeById(dto.id()).orElseThrow(ContactNotFoundByIdException::new);
        boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
        contactMapper.updateEntityFromDto(dto, entity, isConfirmed);
        return contactMapper.toSystemDto(contactRepository.save(entity));
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList,
                                                     ContactChangingListener listener) {
        Map<Long, ContactUpdateDto> contactIdDtoMap = dtoList.stream()
                .collect(Collectors.toMap(ContactUpdateDto::id, Function.identity()));

        List<ContactEntity> entityList = contactRepository.findByOwnerId(ownerId);

        for (ContactEntity entity: entityList) {
            ContactUpdateDto dto = contactIdDtoMap.get(entity.getId());
            boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
            contactMapper.updateEntityFromDto(dto, entity, isConfirmed);
            if (!isConfirmed) {
                listener.callback(dto);
            }
        }
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(entityList));
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
