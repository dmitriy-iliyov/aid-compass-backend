package com.aidcompass.contact.services;

import com.aidcompass.contact.facades.ContactChangingListener;
import com.aidcompass.contact.repositories.ContactRepository;
import com.aidcompass.contact.mappers.ContactMapper;
import com.aidcompass.contact.models.entity.ContactEntity;
import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact_type.ContactTypeService;
import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.base_dto.SystemContactUpdateDto;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.enums.Authority;
import com.aidcompass.enums.ContactType;
import com.aidcompass.exceptions.invalid_input.InvalidAttemptChangeLastPrimaryException;
import com.aidcompass.exceptions.not_found.ContactNotFoundByContactException;
import com.aidcompass.exceptions.not_found.ContactNotFoundByIdException;
import com.aidcompass.contact_filling_progress.ContactProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedContactService implements ContactService, SystemContactService {

    private final ContactRepository repository;
    private final ContactMapper mapper;
    private final ContactTypeService typeService;
    private final ContactProgressService contactProgressService;


    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto) {
        ContactTypeEntity typeEntity = typeService.findByType(dto.type());
        ContactEntity entity = mapper.toEntity(dto);
        entity.setTypeEntity(typeEntity);
        entity.setOwnerId(ownerId);
        //
        if (dto.type().equals(ContactType.PHONE_NUMBER)) {
            entity.setConfirmed(true);
        }
        entity.setPrimary(true);
        //
        entity = repository.save(entity);
        this.notifyAboutProgress(typeEntity.getType(), ownerId);
        return mapper.toPrivateDto(entity);
    }

    @CacheEvict(value = "public_contacts", key = "#result.ownerId.toString()")
    @Transactional
    @Override
    public SystemContactDto save(SystemContactDto dto) {
        ContactTypeEntity typeEntity = typeService.findByType(dto.getType());
        ContactEntity entity = mapper.toEntity(dto);
        entity.setTypeEntity(typeEntity);
        entity = repository.save(entity);
        this.notifyAboutProgress(typeEntity.getType(), dto.getOwnerId());
        return mapper.toSystemDto(entity);
    }

    private void notifyAboutProgress(ContactType type, UUID ownerId) {
        if (!contactProgressService.isComplete(ownerId)) {
            List<Authority> authorityList = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(Authority::valueOf)
                    .toList();

            Authority authority;
            if (authorityList.contains(Authority.ROLE_JURIST)) {
                authority = Authority.ROLE_JURIST;
            } else if (authorityList.contains(Authority.ROLE_DOCTOR)) {
                authority = Authority.ROLE_DOCTOR;
            } else if (authorityList.contains(Authority.ROLE_CUSTOMER)) {
                authority = Authority.ROLE_CUSTOMER;
            } else {
                authority = Authority.ROLE_ANONYMOUS;
            }

            if (type.equals(ContactType.EMAIL)) {
                contactProgressService.emailFilled(ownerId, authority);
            } else if (type.equals(ContactType.PHONE_NUMBER)) {
                contactProgressService.phoneFilled(ownerId, authority);
            }
        }
    }

    @CacheEvict(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional
    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> dtoList) {
        List<ContactEntity> entityList = mapper.toEntityList(dtoList);
        entityList.forEach(contactEntity -> contactEntity.setOwnerId(ownerId));
        return mapper.toPrivateDtoList(repository.saveAll(entityList));
    }

    @Transactional
    @Override
    public void confirmContactById(Long contactId) {
        if (repository.confirmContactById(contactId) == 0) {
            throw new ContactNotFoundByIdException();
        }
    }

    @Transactional
    @Override
    public void markContactAsLinked(UUID ownerId, Long contactId) {
        ContactEntity linkedEntity = repository.findByOwnerIdAndLinkedToAccount(ownerId, true)
                .orElse(null);
        if (linkedEntity != null) {
            linkedEntity.setLinkedToAccount(false);
            repository.save(linkedEntity);
        }
        ContactEntity newLinkedEntity = repository.findById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        newLinkedEntity.setLinkedToAccount(true);
        repository.save(newLinkedEntity);
    }

    @Cacheable(value = "exists", key="#typeEntity.type.toString() + ':' + #contact", condition = "#result == true")
    @Transactional(readOnly = true)
    @Override
    public boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact) {
        return repository.existsByTypeEntityAndContact(typeEntity, contact);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isContactConfirmed(Long contactId) {
        return repository.isContactConfirmed(contactId);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByOwnerIdAndContactType(UUID ownerId, ContactType type) {
        return repository.countByOwnerIdAndContentType(ownerId, type);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = repository.findByOwnerId(ownerId);
        return mapper.toPrivateDtoList(entityList);
    }

    @Cacheable(value = "public_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = repository.findByOwnerIdAndIsConfirmed(ownerId, true);
        return mapper.toPublicDtoList(entityList);
    }

    @Cacheable(value = "primary_contacts", key = "#ownerId.toString()")
    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = repository.findByOwnerIdAndIsPrimary(ownerId, true);
        return mapper.toPublicDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = repository.findByOwnerIdAndIsPrimary(ownerId, false);
        return mapper.toPublicDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemContactDto> findAllByOwnerId(UUID ownerId) {
        List<ContactEntity> entityList = repository.findByOwnerId(ownerId);
        return mapper.toSystemDtoList(entityList);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findByContact(String contact) {
        ContactEntity entity = repository.findByContact(contact)
                .orElseThrow(ContactNotFoundByContactException::new);
        return mapper.toSystemDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemContactDto findById(Long contactId) {
        ContactEntity entity = repository.findWithTypeById(contactId).orElseThrow(ContactNotFoundByIdException::new);
        return mapper.toSystemDto(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    public PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto dto, ContactChangingListener listener) {
        ContactEntity entity = repository.findWithTypeById(dto.id()).orElseThrow(
                ContactNotFoundByIdException::new
        );
        boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
        mapper.updateEntityFromDto(dto, entity, isConfirmed);
        entity = repository.save(entity);
        if (!isConfirmed) {
            listener.callback(dto);
        }
        return mapper.toPrivateDto(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#dto.ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#dto.ownerId.toString()")
    })
    @Transactional
    @Override
    public SystemContactDto update(SystemContactUpdateDto dto) {
        ContactEntity entity = repository.findWithTypeById(dto.id()).orElseThrow(ContactNotFoundByIdException::new);
        boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
        //
        if (dto.type().equals(ContactType.PHONE_NUMBER)) {
            isConfirmed = true;
        }
        entity.setPrimary(true);
        //
        mapper.updateEntityFromDto(dto, entity, isConfirmed);
        return mapper.toSystemDto(repository.save(entity));
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> dtoList,
                                                     ContactChangingListener listener) {

        var result = repository.findByOwnerId(ownerId).stream()
                .collect(Collectors.teeing(
                        Collectors.toMap(ContactEntity::getId, Function.identity()),
                        Collectors.filtering(ContactEntity::isPrimary, Collectors.counting()),
                        Map::entry
                ));

        Map<Long, ContactEntity> entityMap = result.getKey();
        long primaryCount = result.getValue();
        List<ContactEntity> resultEntityList = new ArrayList<>();

        for (ContactUpdateDto dto: dtoList) {
            ContactEntity entity = entityMap.get(dto.id());
            resultEntityList.add(entity);
            boolean isConfirmed = entity.getContact().equals(dto.contact()) && entity.isConfirmed();
            //
            if (dto.type().equals(ContactType.PHONE_NUMBER)) {
                isConfirmed = true;
            }
            entity.setPrimary(true);
            //
            mapper.updateEntityFromDto(dto, entity, isConfirmed);
            if (!isConfirmed) {
                listener.callback(dto);
            }
            if (!isConfirmed && dto.isPrimary() && primaryCount == 1) {
                throw new InvalidAttemptChangeLastPrimaryException();
            }
        }
        return mapper.toPrivateDtoList(repository.saveAll(resultEntityList));
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public void deleteById(UUID ownerId, Long contactId){
        repository.deleteById(contactId);
    }

    @Caching(evict = {
            @CacheEvict(value = "public_contacts", key = "#ownerId.toString()"),
            @CacheEvict(value = "primary_contacts", key = "#ownerId.toString()")
    })
    @Transactional
    @Override
    public void deleteAll(UUID ownerId) {
        repository.deleteAllByOwnerId(ownerId);
    }
}
