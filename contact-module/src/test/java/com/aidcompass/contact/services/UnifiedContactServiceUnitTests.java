//package com.aidcompass.contact.services;
//
//import com.aidcompass.contact.facades.ContactChangingListener;
//import com.aidcompass.contact.repositories.ContactRepository;
//import com.aidcompass.contact.mappers.ContactMapper;
//import com.aidcompass.contact.models.entity.ContactEntity;
//import com.aidcompass.contact.models.dto.*;
//import com.aidcompass.dto.SystemContactDto;
//import com.aidcompass.contact_type.ContactTypeService;
//import com.aidcompass.enums.ContactType;
//import com.aidcompass.contact_type.models.ContactTypeEntity;
//import com.aidcompass.exceptions.not_found.ContactNotFoundByContactException;
//import com.aidcompass.exceptions.not_found.ContactNotFoundByIdException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UnifiedContactServiceUnitTests {
//
//    @Mock
//    private ContactRepository contactRepository;
//
//    @Mock
//    private ContactMapper contactMapper;
//
//    @Mock
//    private ContactTypeService contactTypeService;
//
//    @Mock
//    private ContactChangingListener listener;
//
//    @InjectMocks
//    private UnifiedContactService contactService;
//
//    @Test
//    @DisplayName("UT save() should save contact and return PrivateContactResponseDto")
//    void save_shouldSaveContactAndReturnPrivateContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto contactCreateDto = new ContactCreateDto(ContactType.PHONE_NUMBER, "123456789");
//        ContactEntity contactEntity = new ContactEntity();
//        contactEntity.setOwnerId(ownerId);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, ContactType.PHONE_NUMBER.toString(), "123456789", false, false);
//
//        when(contactMapper.toEntity(contactCreateDto)).thenReturn(contactEntity);
//        when(contactRepository.save(contactEntity)).thenReturn(contactEntity);
//        when(contactMapper.toPrivateResponseDto(contactEntity)).thenReturn(responseDto);
//
//        PrivateContactResponseDto result = contactService.save(ownerId, contactCreateDto);
//
//        assertEquals(responseDto, result);
//        verify(contactRepository).save(contactEntity);
//    }
//
//    @Test
//    @DisplayName("UT saveAll() should save multiple contacts and return list of PrivateContactResponseDto")
//    void saveAll_shouldSaveMultipleContactsAndReturnListOfPrivateContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> contactCreateDtos = List.of(
//                new ContactCreateDto(ContactType.PHONE_NUMBER, "123456789"),
//                new ContactCreateDto(ContactType.EMAIL, "test@example.com")
//        );
//        List<ContactEntity> contactEntities = List.of(new ContactEntity(), new ContactEntity());
//        List<PrivateContactResponseDto> responseDtos = List.of(
//                new PrivateContactResponseDto(1L, "phone_number", "123456789", false, false),
//                new PrivateContactResponseDto(2L, "email", "test@example.com", false, false)
//        );
//
//        when(contactMapper.toEntityList(contactCreateDtos)).thenReturn(contactEntities);
//        when(contactRepository.saveAll(contactEntities)).thenReturn(contactEntities);
//        when(contactMapper.toPrivateResponseDtoList(contactEntities)).thenReturn(responseDtos);
//
//        List<PrivateContactResponseDto> result = contactService.saveAll(ownerId, contactCreateDtos);
//
//        assertEquals(responseDtos, result);
//        verify(contactRepository).saveAll(contactEntities);
//    }
//
//    @Test
//    @DisplayName("UT saveAll() should return empty list when input list is empty")
//    void saveAll_shouldReturnEmptyListWhenInputListIsEmpty() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> emptyList = Collections.emptyList();
//
//        when(contactMapper.toEntityList(emptyList)).thenReturn(Collections.emptyList());
//        when(contactRepository.saveAll(Collections.emptyList())).thenReturn(Collections.emptyList());
//        when(contactMapper.toPrivateResponseDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());
//
//        List<PrivateContactResponseDto> result = contactService.saveAll(ownerId, emptyList);
//
//        assertTrue(result.isEmpty());
//        verify(contactRepository).saveAll(Collections.emptyList());
//    }
//
//    @Test
//    @DisplayName("UT markContactAsLinked() should mark the contact as linked and unlinked others")
//    void markContactAsLinked_shouldMarkContactAsLinkedAndUnlinkedOthers() {
//        UUID ownerId = UUID.randomUUID();
//        Long contactId = 1L;
//        ContactEntity linked = new ContactEntity();
//        linked.setLinkedToAccount(true);
//        ContactEntity target = new ContactEntity();
//        target.setId(contactId);
//
//        when(contactRepository.findByOwnerIdAndLinkedToAccount(ownerId, true)).thenReturn(Optional.of(linked));
//        when(contactRepository.findById(contactId)).thenReturn(Optional.of(target));
//
//        contactService.markContactAsLinked(ownerId, contactId);
//
//        assertFalse(linked.isLinkedToAccount());
//        assertTrue(target.isLinkedToAccount());
//        verify(contactRepository).save(linked);
//        verify(contactRepository).save(target);
//    }
//
//    @Test
//    @DisplayName("UT markContactAsLinked() should throw ContactNotFoundByIdException if contact not found")
//    void markContactAsLinked_shouldThrowExceptionWhenNewContactNotFound() {
//        UUID ownerId = UUID.randomUUID();
//        Long id = 42L;
//
//        when(contactRepository.findByOwnerIdAndLinkedToAccount(ownerId, true)).thenReturn(Optional.empty());
//        when(contactRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(ContactNotFoundByIdException.class, () -> contactService.markContactAsLinked(ownerId, id));
//    }
//
//    @Test
//    @DisplayName("UT existsByContactTypeAndContact() should return true when contact exists")
//    void existsByContactTypeAndContact_shouldReturnTrueWhenContactExistsEntity() {
//        ContactType type = ContactType.PHONE_NUMBER;
//        ContactTypeEntity typeEntity = new ContactTypeEntity(1, type);
//        String contact = "123456789";
//
//        when(contactRepository.existsByTypeEntityAndContact(typeEntity, contact)).thenReturn(true);
//
//        assertTrue(contactService.existsByTypeEntityAndContact(typeEntity, contact));
//    }
//
//    @Test
//    @DisplayName("UT existsByContactTypeAndContact() should return false when contact does not exist")
//    void existsByContactTypeAnd_shouldReturnFalseWhenContactDoesNotExistEntity() {
//        ContactType type = ContactType.EMAIL;
//        String contact = "none@example.com";
//        ContactTypeEntity typeEntity = new ContactTypeEntity(1, type);
//
//        when(contactRepository.existsByTypeEntityAndContact(typeEntity, contact)).thenReturn(false);
//
//        assertFalse(contactService.existsByTypeEntityAndContact(typeEntity, contact));
//    }
//
//    @Test
//    @DisplayName("UT findAllPrivateByOwnerId() should return list of PrivateContactResponseDto")
//    void findAllPrivateByOwnerId_shouldReturnListOfPrivateContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> entities = List.of(new ContactEntity(), new ContactEntity());
//        List<PrivateContactResponseDto> dtos = List.of(
//                new PrivateContactResponseDto(1L, "phone_number", "123456789", false, false),
//                new PrivateContactResponseDto(2L, "email", "test@example.com", false, false)
//        );
//
//        when(contactRepository.findByOwnerId(ownerId)).thenReturn(entities);
//        when(contactMapper.toPrivateResponseDtoList(entities)).thenReturn(dtos);
//
//        List<PrivateContactResponseDto> result = contactService.findAllPrivateByOwnerId(ownerId);
//
//        assertEquals(dtos, result);
//    }
//
//    @Test
//    @DisplayName("UT findByContact() should return SystemContactDto when contact exists")
//    void findByContact_shouldReturnSystemContactDtoWhenContactExists() {
//        String contact = "123456789";
//        ContactEntity entity = new ContactEntity();
//        SystemContactDto dto = new SystemContactDto(1L, UUID.randomUUID(), ContactType.PHONE_NUMBER, "123456789", false, false, false);
//
//        when(contactRepository.findByContact(contact)).thenReturn(Optional.of(entity));
//        when(contactMapper.toSystemDto(entity)).thenReturn(dto);
//
//        assertEquals(dto, contactService.findByContact(contact));
//    }
//
//    @Test
//    @DisplayName("UT countByOwnerIdAndContactType() should return correct count when contacts exist")
//    void countByOwnerIdAndContactType_shouldReturnCorrectCount() {
//        UUID ownerId = UUID.randomUUID();
//        ContactType contactType = ContactType.EMAIL;
//        long expectedCount = 3L;
//
//        when(contactRepository.countByOwnerIdAndContentType(ownerId, contactType)).thenReturn(expectedCount);
//
//        long result = contactService.countByOwnerIdAndContactType(ownerId, contactType);
//
//        assertEquals(expectedCount, result);
//        verify(contactRepository, times(1)).countByOwnerIdAndContentType(ownerId, contactType);
//    }
//
//    @Test
//    @DisplayName("UT countByOwnerIdAndContactType() should return zero when no contacts found")
//    void countByOwnerIdAndContactType_shouldReturnZeroWhenNoContactsFound() {
//        UUID ownerId = UUID.randomUUID();
//        ContactType contactType = ContactType.PHONE_NUMBER;
//
//        when(contactRepository.countByOwnerIdAndContentType(ownerId, contactType)).thenReturn(0L);
//
//        long result = contactService.countByOwnerIdAndContactType(ownerId, contactType);
//
//        assertEquals(0L, result);
//        verify(contactRepository, times(1)).countByOwnerIdAndContentType(ownerId, contactType);
//    }
//
//    @Test
//    @DisplayName("UT findByContact() should throw ContactNotFoundByContactException when contact is not found")
//    void findByContact_shouldThrowExceptionWhenContactNotFound() {
//        when(contactRepository.findByContact("none")).thenReturn(Optional.empty());
//        assertThrows(ContactNotFoundByContactException.class, () -> contactService.findByContact("none"));
//    }
//
//    @Test
//    @DisplayName("UT findAllPublicByOwnerId() should return list of PublicContactResponseDto when confirmed contacts exist")
//    void findAllPublicByOwnerId_shouldReturnListOfPublicContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> contactEntities = Arrays.asList(new ContactEntity(), new ContactEntity());
//        List<PublicContactResponseDto> expectedDtos = Arrays.asList(
//                new PublicContactResponseDto(ContactType.EMAIL.toString(), "user1@example.com", true),
//                new PublicContactResponseDto(ContactType.PHONE_NUMBER.toString(), "123456789", true)
//        );
//
//        when(contactRepository.findByOwnerIdAndIsConfirmed(ownerId, true)).thenReturn(contactEntities);
//        when(contactMapper.toPublicResponseDtoList(contactEntities)).thenReturn(expectedDtos);
//
//        List<PublicContactResponseDto> result = contactService.findAllPublicByOwnerId(ownerId);
//
//        assertEquals(expectedDtos, result);
//        verify(contactRepository, times(1)).findByOwnerIdAndIsConfirmed(ownerId, true);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(contactEntities);
//    }
//
//    @Test
//    @DisplayName("UT findAllPublicByOwnerId() should return empty list when no confirmed contacts found")
//    void findAllPublicByOwnerId_shouldReturnEmptyListWhenNoContactsFound() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> emptyEntities = Collections.emptyList();
//
//        when(contactRepository.findByOwnerIdAndIsConfirmed(ownerId, true)).thenReturn(emptyEntities);
//        when(contactMapper.toPublicResponseDtoList(emptyEntities)).thenReturn(Collections.emptyList());
//
//        List<PublicContactResponseDto> result = contactService.findAllPublicByOwnerId(ownerId);
//
//        assertTrue(result.isEmpty());
//        verify(contactRepository, times(1)).findByOwnerIdAndIsConfirmed(ownerId, true);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(emptyEntities);
//    }
//
//    @Test
//    @DisplayName("UT findPrimaryByOwnerId() should return list of PublicContactResponseDto when primary contacts exist")
//    void findPrimaryByOwnerId_shouldReturnListOfPublicContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> contactEntities = Arrays.asList(new ContactEntity(), new ContactEntity());
//        List<PublicContactResponseDto> expectedDtos = Arrays.asList(
//                new PublicContactResponseDto(ContactType.EMAIL.toString(), "primary1@example.com", true),
//                new PublicContactResponseDto(ContactType.PHONE_NUMBER.toString(), "123456789", true)
//        );
//
//        when(contactRepository.findByOwnerIdAndIsPrimary(ownerId, true)).thenReturn(contactEntities);
//        when(contactMapper.toPublicResponseDtoList(contactEntities)).thenReturn(expectedDtos);
//
//        List<PublicContactResponseDto> result = contactService.findPrimaryByOwnerId(ownerId);
//
//        assertEquals(expectedDtos, result);
//        verify(contactRepository, times(1)).findByOwnerIdAndIsPrimary(ownerId, true);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(contactEntities);
//    }
//
//    @Test
//    @DisplayName("UT findPrimaryByOwnerId() should return empty list when no primary contacts found")
//    void findPrimaryByOwnerId_shouldReturnEmptyListWhenNoneExist() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> emptyList = Collections.emptyList();
//
//        when(contactRepository.findByOwnerIdAndIsPrimary(ownerId, true)).thenReturn(emptyList);
//        when(contactMapper.toPublicResponseDtoList(emptyList)).thenReturn(Collections.emptyList());
//
//        List<PublicContactResponseDto> result = contactService.findPrimaryByOwnerId(ownerId);
//
//        assertTrue(result.isEmpty());
//        verify(contactRepository, times(1)).findByOwnerIdAndIsPrimary(ownerId, true);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(emptyList);
//    }
//
//    @Test
//    @DisplayName("UT findSecondaryByOwnerId() should return list of PublicContactResponseDto when secondary contacts exist")
//    void findSecondaryByOwnerId_shouldReturnListOfPublicContactResponseDto() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> contactEntities = Arrays.asList(new ContactEntity(), new ContactEntity());
//        List<PublicContactResponseDto> expectedDtos = Arrays.asList(
//                new PublicContactResponseDto(ContactType.EMAIL.toString(), "secondary1@example.com", true),
//                new PublicContactResponseDto(ContactType.PHONE_NUMBER.toString(), "987654321", true)
//        );
//
//        when(contactRepository.findByOwnerIdAndIsPrimary(ownerId, false)).thenReturn(contactEntities);
//        when(contactMapper.toPublicResponseDtoList(contactEntities)).thenReturn(expectedDtos);
//
//        List<PublicContactResponseDto> result = contactService.findSecondaryByOwnerId(ownerId);
//
//        assertEquals(expectedDtos, result);
//        verify(contactRepository, times(1)).findByOwnerIdAndIsPrimary(ownerId, false);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(contactEntities);
//    }
//
//    @Test
//    @DisplayName("UT findSecondaryByOwnerId() should return empty list when no secondary contacts found")
//    void findSecondaryByOwnerId_shouldReturnEmptyListWhenNoneExist() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> emptyList = Collections.emptyList();
//
//        when(contactRepository.findByOwnerIdAndIsPrimary(ownerId, false)).thenReturn(emptyList);
//        when(contactMapper.toPublicResponseDtoList(emptyList)).thenReturn(Collections.emptyList());
//
//        List<PublicContactResponseDto> result = contactService.findSecondaryByOwnerId(ownerId);
//
//        assertTrue(result.isEmpty());
//        verify(contactRepository, times(1)).findByOwnerIdAndIsPrimary(ownerId, false);
//        verify(contactMapper, times(1)).toPublicResponseDtoList(emptyList);
//    }
//
//    @Test
//    @DisplayName("UT findAllByOwnerId() should return mapped list when contacts found")
//    void findAllByOwnerId_shouldReturnMappedListWhenContactsFound() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactEntity> contactEntities = List.of(new ContactEntity(), new ContactEntity());
//        List<SystemContactDto> expectedDtos = List.of(
//                new SystemContactDto(1L, ownerId, ContactType.EMAIL, "email@example.com", true, false, true),
//                new SystemContactDto(2L, ownerId, ContactType.PHONE_NUMBER, "123456789", false, true, false)
//        );
//
//        when(contactRepository.findByOwnerId(ownerId)).thenReturn(contactEntities);
//        when(contactMapper.toSystemDtoList(contactEntities)).thenReturn(expectedDtos);
//
//        List<SystemContactDto> result = contactService.findAllByOwnerId(ownerId);
//
//        assertEquals(expectedDtos, result);
//        verify(contactRepository).findByOwnerId(ownerId);
//        verify(contactMapper).toSystemDtoList(contactEntities);
//    }
//
//    @Test
//    @DisplayName("UT findAllByOwnerId() should return empty list when no contacts found")
//    void findAllByOwnerId_shouldReturnEmptyListWhenNoContactsFound() {
//        UUID ownerId = UUID.randomUUID();
//
//        when(contactRepository.findByOwnerId(ownerId)).thenReturn(Collections.emptyList());
//        when(contactMapper.toSystemDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());
//
//        List<SystemContactDto> result = contactService.findAllByOwnerId(ownerId);
//
//        assertTrue(result.isEmpty());
//        verify(contactRepository).findByOwnerId(ownerId);
//        verify(contactMapper).toSystemDtoList(Collections.emptyList());
//    }
//
////    @Test
////    @DisplayName("UT updateById() should update contact and return PrivateContactResponseDto")
////    void updateById_shouldUpdateContactAndReturnPrivateContactResponseDto() {
////        ContactUpdateDto dto = new ContactUpdateDto(1L, ContactType.EMAIL, "updated@example.com", false);
////        ContactEntity entity = new ContactEntity();
////        entity.setId(dto.id());
////        PrivateContactResponseDto response = new PrivateContactResponseDto(1L, "email", "updated@example.com", false, false);
////
////        when(contactRepository.findById(dto.id())).thenReturn(Optional.of(entity));
////        when(contactRepository.save(entity)).thenReturn(entity);
////        when(contactMapper.toPrivateResponseDto(entity)).thenReturn(response);
////
//////        assertEquals(response, contactService.update(UUID.randomUUID(), dto));
////    }
//
//    @Test
//    @DisplayName("UT isContactConfirmed() should return true when contact is confirmed")
//    void isContactConfirmed_shouldReturnTrueWhenConfirmed() {
//        Long contactId = 100L;
//
//        when(contactRepository.isContactConfirmed(contactId)).thenReturn(true);
//
//        boolean result = contactService.isContactConfirmed(contactId);
//
//        assertTrue(result);
//        verify(contactRepository).isContactConfirmed(contactId);
//    }
//
//    @Test
//    @DisplayName("UT isContactConfirmed() should return false when contact is not confirmed")
//    void isContactConfirmed_shouldReturnFalseWhenNotConfirmed() {
//        Long contactId = 101L;
//
//        when(contactRepository.isContactConfirmed(contactId)).thenReturn(false);
//
//        boolean result = contactService.isContactConfirmed(contactId);
//
//        assertFalse(result);
//        verify(contactRepository).isContactConfirmed(contactId);
//    }
//
////    @Test
////    @DisplayName("UT updateAll() should update all matched contacts and return updated list")
////    void updateAll_shouldUpdateAllMatchedContactsAndReturnUpdatedList() {
////        UUID ownerId = UUID.randomUUID();
////
////        ContactUpdateDto dto1 = new ContactUpdateDto(1L, ContactType.EMAIL, "updated1@example.com", true);
////        ContactUpdateDto dto2 = new ContactUpdateDto(2L, ContactType.PHONE_NUMBER, "987654321", false);
////        List<ContactUpdateDto> dtoList = List.of(dto1, dto2);
////
////        ContactEntity entity1 = new ContactEntity();
////        entity1.setId(1L);
////        ContactEntity entity2 = new ContactEntity();
////        entity2.setId(2L);
////        List<ContactEntity> entities = List.of(entity1, entity2);
////
////        List<ContactEntity> savedEntities = List.of(entity1, entity2);
////        List<PrivateContactResponseDto> expectedResponse = List.of(
////                new PrivateContactResponseDto(1L, "EMAIL", "updated1@example.com", true, false),
////                new PrivateContactResponseDto(2L, "PHONE_NUMBER", "987654321", false, false)
////        );
////
////        when(contactRepository.findByOwnerId(ownerId)).thenReturn(entities);
////        doNothing().when(contactMapper).updateEntityFromDto(dto1, entity1);
////        doNothing().when(contactMapper).updateEntityFromDto(dto2, entity2);
////        when(contactRepository.saveAll(entities)).thenReturn(savedEntities);
////        when(contactMapper.toPrivateResponseDtoList(savedEntities)).thenReturn(expectedResponse);
////
////        List<PrivateContactResponseDto> result = contactService.updateAll(ownerId, dtoList);
////
////        assertEquals(expectedResponse, result);
////        verify(contactRepository).findByOwnerId(ownerId);
////        verify(contactRepository).saveAll(entities);
////        verify(contactMapper).updateEntityFromDto(dto1, entity1);
////        verify(contactMapper).updateEntityFromDto(dto2, entity2);
////        verify(contactMapper).toPrivateResponseDtoList(savedEntities);
////    }
////
////    @Test
////    @DisplayName("UT updateAll() should skip unmatched contact IDs")
////    void updateAll_shouldSkipUnmatchedContactIds() {
////        UUID ownerId = UUID.randomUUID();
////
////        ContactUpdateDto dto1 = new ContactUpdateDto(99L, ContactType.EMAIL, "ghost@example.com", true);
////        List<ContactUpdateDto> dtoList = List.of(dto1);
////
////        ContactEntity entity1 = new ContactEntity();
////        entity1.setId(1L);
////        List<ContactEntity> entities = List.of(entity1);
////
////        List<PrivateContactResponseDto> expectedResponse = List.of(); // ничего не обновлено
////
////        when(contactRepository.findByOwnerId(ownerId)).thenReturn(entities);
////        when(contactRepository.saveAll(entities)).thenReturn(entities);
////        when(contactMapper.toPrivateResponseDtoList(entities)).thenReturn(expectedResponse);
////
////        List<PrivateContactResponseDto> result = contactService.updateAll(ownerId, dtoList);
////
////        assertEquals(expectedResponse, result);
////        verify(contactRepository).findByOwnerId(ownerId);
////        verify(contactRepository).saveAll(entities);
////        verify(contactMapper).toPrivateResponseDtoList(entities);
////        verify(contactMapper, never()).updateEntityFromDto(any(ContactUpdateDto.class), any(ContactEntity.class));
////    }
////
////    @Test
////    @DisplayName("UT updateAll() should return unchanged entities when update list is empty")
////    void updateAll_shouldReturnUnchangedWhenUpdateListIsEmpty() {
////        UUID ownerId = UUID.randomUUID();
////        List<ContactUpdateDto> dtoList = List.of();
////
////        List<ContactEntity> entities = List.of(new ContactEntity());
////        List<PrivateContactResponseDto> expectedResponse = List.of(
////                new PrivateContactResponseDto(1L, "EMAIL", "original@example.com", true, false)
////        );
////
////        when(contactRepository.findByOwnerId(ownerId)).thenReturn(entities);
////        when(contactRepository.saveAll(entities)).thenReturn(entities);
////        when(contactMapper.toPrivateResponseDtoList(entities)).thenReturn(expectedResponse);
////
////        List<PrivateContactResponseDto> result = contactService.updateAll(ownerId, dtoList);
////
////        assertEquals(expectedResponse, result);
////        verify(contactRepository).findByOwnerId(ownerId);
////        verify(contactRepository).saveAll(entities);
////        verify(contactMapper).toPrivateResponseDtoList(entities);
////        verify(contactMapper, never()).updateEntityFromDto(any(ContactUpdateDto.class), any(ContactEntity.class));
////    }
////
////    @Test
////    @DisplayName("UT updateAll() should return empty list when owner has no contacts")
////    void updateAll_shouldReturnEmptyWhenNoContacts() {
////        UUID ownerId = UUID.randomUUID();
////        List<ContactUpdateDto> dtoList = List.of(
////                new ContactUpdateDto(1L, ContactType.EMAIL, "updated@example.com", true)
////        );
////
////        when(contactRepository.findByOwnerId(ownerId)).thenReturn(Collections.emptyList());
////        when(contactMapper.toPrivateResponseDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());
////
////        List<PrivateContactResponseDto> result = contactService.updateAll(ownerId, dtoList);
////
////        assertTrue(result.isEmpty());
////        verify(contactRepository).findByOwnerId(ownerId);
////        verify(contactMapper).toPrivateResponseDtoList(Collections.emptyList());
////        verify(contactMapper, never()).updateEntityFromDto(any(), any());
////    }
////
////    @Test
////    @DisplayName("UT updateById() should throw ContactNotFoundByIdException when contact is not found")
////    void update_shouldThrowExceptionWhenContactNotFound() {
////        ContactUpdateDto dto = new ContactUpdateDto(1L, ContactType.EMAIL, "updated@example.com", false);
////        when(contactRepository.findById(dto.id())).thenReturn(Optional.empty());
////
////        assertThrows(ContactNotFoundByIdException.class, () -> contactService.update(UUID.randomUUID(), dto));
////    }
//
//    @Test
//    @DisplayName("UT deleteById() should delete contact by id")
//    void deleteById_shouldDeleteContactById() {
//        contactService.deleteById(UUID.randomUUID(), 1L);
//        verify(contactRepository).deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("UT deleteAll() should delete all contacts by owner id")
//    void deleteAll_shouldDeleteAllContactsByOwnerId() {
//        UUID ownerId = UUID.randomUUID();
//        contactService.deleteAll(ownerId);
//        verify(contactRepository).deleteAllByOwnerId(ownerId);
//    }
//}