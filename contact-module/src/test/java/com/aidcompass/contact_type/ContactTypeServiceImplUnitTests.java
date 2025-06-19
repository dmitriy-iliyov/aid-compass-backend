//package com.aidcompass.contact_type;
//
//import com.aidcompass.contact_type.mapper.ContactTypeMapper;
//import com.aidcompass.contact_type.models.ContactTypeDto;
//import com.aidcompass.contact_type.models.ContactTypeEntity;
//import com.aidcompass.enums.ContactType;
//import com.aidcompass.exceptions.not_found.ContentTypeNotFoundByTypeException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ContactTypeServiceImplUnitTests {
//
//    @Mock
//    ContactTypeRepository repository;
//
//    @Mock
//    ContactTypeMapper mapper;
//
//    @InjectMocks
//    ContactTypeServiceImpl service;
//
//
//    @Test
//    @DisplayName("UT saveAll() should save and return list of ContactTypeDto")
//    void saveAll_shouldSaveAndReturnDtos() {
//        List<ContactType> input = List.of(ContactType.EMAIL);
//        List<ContactTypeEntity> entities = List.of(new ContactTypeEntity(1, ContactType.EMAIL));
//        List<ContactTypeDto> dtos = List.of(new ContactTypeDto(1, ContactType.EMAIL));
//
//        when(mapper.toEntityList(input)).thenReturn(entities);
//        when(repository.saveAll(entities)).thenReturn(entities);
//        when(mapper.toDtoList(entities)).thenReturn(dtos);
//
//        List<ContactTypeDto> result = service.saveAll(input);
//
//        assertEquals(dtos, result);
//        verify(repository).saveAll(entities);
//    }
//
//    @Test
//    @DisplayName("UT findByType(type) should return ContactTypeEntity when found")
//    void findByType_shouldReturnEntity_whenExists() {
//        ContactType type = ContactType.PHONE_NUMBER;
//        ContactTypeEntity entity = new ContactTypeEntity(2, type);
//
//        when(repository.findByType(type)).thenReturn(Optional.of(entity));
//
//        ContactTypeEntity result = service.findByType(type);
//
//        assertEquals(entity, result);
//        verify(repository).findByType(type);
//    }
//
//    @Test
//    @DisplayName("UT findByType(type) should throw ContentTypeNotFoundByTypeException when not found")
//    void findByType_shouldThrowException_whenNotFound() {
//        ContactType type = ContactType.EMAIL;
//
//        when(repository.findByType(type)).thenReturn(Optional.empty());
//
//        assertThrows(ContentTypeNotFoundByTypeException.class, () -> service.findByType(type));
//    }
//}
