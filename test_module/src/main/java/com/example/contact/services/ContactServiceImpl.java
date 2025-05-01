package com.example.contact.services;

import com.example.contact.ContactRepository;
import com.example.contact.mapper.ContactMapper;
import com.example.contact.models.ContactEntity;
import com.example.contact.models.dto.*;
import com.example.contact_type.models.ContactType;
import com.example.contact_type.models.ContactTypeEntity;
import com.example.contact_type.ContactTypeService;
import com.example.exceptions.not_found.ContactNotFoundByContactException;
import com.example.exceptions.not_found.ContactNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// метод для установки контакта привязки который по рестовому обращяеться с PUT запросом
// (чтоб можно было и новый установить одноврименно с добавлением и старый просто пометив флагом)
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService, SystemContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ContactTypeService contactTypeService;


    @Transactional
    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto contactCreateDto) {
        System.out.println(contactCreateDto);
        ContactEntity contactEntity = contactMapper.toEntity(contactCreateDto);
        contactEntity.setOwnerId(ownerId);
        System.out.println(contactEntity);
        return contactMapper.toPrivateResponseDto(contactRepository.save(contactEntity));
    }

    @Transactional
    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contactCreateDtoList) {
        List<ContactEntity> contactEntityList = contactMapper.toEntityList(contactCreateDtoList);
        contactEntityList.forEach(contactEntity -> contactEntity.setOwnerId(ownerId));
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(contactEntityList));
    }

    @Transactional
    @Override
    public void markContactAsLinked(UUID ownerId, Long id) {
        ContactEntity linkedContactEntity = contactRepository.findByOwnerIdAndLinkedToAccount(ownerId, true)
                .orElse(null);

        if (linkedContactEntity != null) {
            linkedContactEntity.setLinkedToAccount(false);
            contactRepository.save(linkedContactEntity);
        }

        ContactEntity contactEntity = contactRepository.findById(id).orElseThrow(ContactNotFoundByIdException::new);
        contactEntity.setLinkedToAccount(true);

        contactRepository.save(contactEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByContactTypeAndContact(ContactType type, String phoneNumber) {
        ContactTypeEntity typeEntity = contactTypeService.findByType(type);
        return contactRepository.existsByTypeEntityAndContact(typeEntity, phoneNumber);
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

    @Transactional(readOnly = true)
    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerIdAndIsConfirmed(ownerId, true);
        return contactMapper.toPublicResponseDtoList(contactEntityList);
    }

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
    public boolean isContactConfirmed(Long id) {
        return contactRepository.isContactConfirmed(id);
    }

    @Transactional
    @Override
    public PrivateContactResponseDto updateById(ContactUpdateDto contactUpdateDto) {
        ContactEntity contactEntity = contactRepository.findById(contactUpdateDto.id()).orElseThrow(
                ContactNotFoundByIdException::new
        );
        contactMapper.updateEntityFromDto(contactUpdateDto, contactEntity);
        return contactMapper.toPrivateResponseDto(contactRepository.save(contactEntity));
    }

    @Transactional
    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList) {
        Map<Long, ContactUpdateDto> contactIdDtoMap = contactUpdateDtoList.stream()
                .collect(Collectors.toMap(ContactUpdateDto::id, Function.identity()));
        List<ContactEntity> contactEntityList = contactRepository.findByOwnerId(ownerId);
        for (ContactEntity contactEntity: contactEntityList) {
            ContactUpdateDto contactUpdateDto = contactIdDtoMap.get(contactEntity.getId());
            contactMapper.updateEntityFromDto(contactUpdateDto, contactEntity);
        }
        return contactMapper.toPrivateResponseDtoList(contactRepository.saveAll(contactEntityList));
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        contactRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll(UUID ownerId) {
        contactRepository.deleteAllByOwnerId(ownerId);
    }
}
