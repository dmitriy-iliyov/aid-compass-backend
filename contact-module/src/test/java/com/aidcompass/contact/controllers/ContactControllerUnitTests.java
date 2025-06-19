//package com.aidcompass.contact.controllers;
//
//import com.aidcompass.contact.models.dto.*;
//import com.aidcompass.contact.facades.GeneralContactOrchestrator;
//import com.aidcompass.enums.ContactType;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ContactControllerUnitTests {
//
//    @Mock
//    GeneralContactOrchestrator generalContactOrchestrator;
//
//    @InjectMocks
//    ContactController contactController;
//
//    static ObjectMapper objectMapper;
//
//
//    @BeforeAll
//    static void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    @DisplayName("UT createContact() should return 201 CREATED with valid email from JSON")
//    void createContact_whenEmailValidFromJson_shouldReturn201() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto privateDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//
//        when(generalContactOrchestrator.save(ownerId, dto)).thenReturn(privateDto);
//
//        ResponseEntity<PrivateContactResponseDto> response = contactController.createContact(ownerId, dto);
//
//        assertEquals(privateDto, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(generalContactOrchestrator, times(1)).save(ownerId, dto);
//    }
//
//    @Test
//    @DisplayName("UT createContact() should handle contactFacade exception")
//    void createContact_whenFacadeThrows_shouldThrowException() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
//
//        when(generalContactOrchestrator.save(ownerId, dto)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.createContact(ownerId, dto));
//        verify(generalContactOrchestrator, times(1)).save(ownerId, dto);
//    }
//
//    @Test
//    @DisplayName("UT createContact() should return 201 CREATED with valid phone number from JSON")
//    void createContact_whenPhoneValidFromJson_shouldReturn201() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class);
//        PrivateContactResponseDto privateDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//
//        when(generalContactOrchestrator.save(ownerId, dto)).thenReturn(privateDto);
//
//        ResponseEntity<PrivateContactResponseDto> response = contactController.createContact(ownerId, dto);
//
//        assertEquals(privateDto, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(generalContactOrchestrator, times(1)).save(ownerId, dto);
//    }
//
//    @Test
//    @DisplayName("UT createContacts() should return 201 CREATED with valid contacts list")
//    void createContacts_whenValidContactsList_shouldReturn201() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtoList = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class)
//        );
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtoList);
//
//        List<PrivateContactResponseDto> responseDtoList = dtoList.stream()
//                .map(dto -> new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false))
//                .collect(Collectors.toList());
//
//        when(generalContactOrchestrator.saveAll(ownerId, dtoList)).thenReturn(responseDtoList);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.createContacts(ownerId, wrappedDtoList);
//
//        assertEquals(responseDtoList, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(generalContactOrchestrator, times(1)).saveAll(ownerId, dtoList);
//    }
//
//    @Test
//    @DisplayName("UT createContacts() should handle contactFacade exception")
//    void createContacts_whenFacadeThrows_shouldThrowException() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtoList = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class)
//        );
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtoList);
//
//        when(generalContactOrchestrator.saveAll(ownerId, dtoList)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.createContacts(ownerId, wrappedDtoList));
//        verify(generalContactOrchestrator, times(1)).saveAll(ownerId, dtoList);
//    }
//
//    @Test
//    @DisplayName("UT createContacts() should return 201 CREATED with single contact")
//    void createContacts_whenSingleContact_shouldReturn201() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
//        List<ContactCreateDto> dtoList = List.of(dto);
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtoList);
//
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        List<PrivateContactResponseDto> responseDtoList = List.of(responseDto);
//
//        when(generalContactOrchestrator.saveAll(ownerId, dtoList)).thenReturn(responseDtoList);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.createContacts(ownerId, wrappedDtoList);
//
//        assertEquals(responseDtoList, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(generalContactOrchestrator, times(1)).saveAll(ownerId, dtoList);
//    }
//
//    @Test
//    @DisplayName("UT linkEmailToAccount() should return 204 NO_CONTENT when successful")
//    void linkEmailToAccount_whenSuccessful_shouldReturn204() {
//        UUID ownerId = UUID.randomUUID();
//        Long contactId = 1L;
//
//        doNothing().when(generalContactOrchestrator).markEmailAsLinkedToAccount(ownerId, contactId);
//
//        ResponseEntity<?> response = contactController.linkEmailToAccount(ownerId, contactId);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(generalContactOrchestrator, times(1)).markEmailAsLinkedToAccount(ownerId, contactId);
//    }
//
//    @Test
//    @DisplayName("UT linkEmailToAccount() should handle contactFacade exception")
//    void linkEmailToAccount_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//        Long contactId = 1L;
//
//        doThrow(new RuntimeException("Test exception")).when(generalContactOrchestrator).markEmailAsLinkedToAccount(ownerId, contactId);
//
//        assertThrows(RuntimeException.class, () -> contactController.linkEmailToAccount(ownerId, contactId));
//        verify(generalContactOrchestrator, times(1)).markEmailAsLinkedToAccount(ownerId, contactId);
//    }
//
//    @Test
//    @DisplayName("UT getPrimaryContacts() should return 200 OK with list of primary contacts")
//    void getPrimaryContacts_whenSuccessful_shouldReturn200WithContacts() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> expectedContacts = List.of(
//                new PublicContactResponseDto("EMAIL", "test@example.com", true),
//                new PublicContactResponseDto("PHONE", "+1234567890", false)
//        );
//
//        when(generalContactOrchestrator.findPrimaryByOwnerId(ownerId)).thenReturn(expectedContacts);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getPrimaryContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedContacts, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findPrimaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPrimaryContacts() should return empty list when no primary contacts found")
//    void getPrimaryContacts_whenNoContacts_shouldReturnEmptyList() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> emptyList = Collections.emptyList();
//
//        when(generalContactOrchestrator.findPrimaryByOwnerId(ownerId)).thenReturn(emptyList);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getPrimaryContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(emptyList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findPrimaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPrimaryContacts() should handle contactFacade exception")
//    void getPrimaryContacts_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//
//        when(generalContactOrchestrator.findPrimaryByOwnerId(ownerId)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.getPrimaryContacts(ownerId));
//        verify(generalContactOrchestrator, times(1)).findPrimaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getSecondaryContacts() should return 200 OK with list of secondary contacts")
//    void getSecondaryContacts_whenSuccessful_shouldReturn200WithContacts() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> expectedContacts = List.of(
//                new PublicContactResponseDto("email", "secondary@example.com", true),
//                new PublicContactResponseDto("phone_number", "+0987654321", true)
//        );
//
//        when(generalContactOrchestrator.findSecondaryByOwnerId(ownerId)).thenReturn(expectedContacts);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getSecondaryContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedContacts, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findSecondaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getSecondaryContacts() should return empty list when no secondary contacts found")
//    void getSecondaryContacts_whenNoContacts_shouldReturnEmptyList() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> emptyList = Collections.emptyList();
//
//        when(generalContactOrchestrator.findSecondaryByOwnerId(ownerId)).thenReturn(emptyList);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getSecondaryContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(emptyList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findSecondaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getSecondaryContacts() should handle contactFacade exception")
//    void getSecondaryContacts_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//
//        when(generalContactOrchestrator.findSecondaryByOwnerId(ownerId)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.getSecondaryContacts(ownerId));
//        verify(generalContactOrchestrator, times(1)).findSecondaryByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPrivateContacts() should return 200 OK with list of private contacts")
//    void getPrivateContacts_whenSuccessful_shouldReturn200WithContacts() {
//        UUID ownerId = UUID.randomUUID();
//        List<PrivateContactResponseDto> expectedContacts = List.of(
//                new PrivateContactResponseDto(1L, "EMAIL", "test@example.com", true, false),
//                new PrivateContactResponseDto(2L, "PHONE", "+1234567890", false, true)
//        );
//
//        when(generalContactOrchestrator.findAllPrivateByOwnerId(ownerId)).thenReturn(expectedContacts);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.getPrivateContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedContacts, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findAllPrivateByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPrivateContacts() should return empty list when no private contacts found")
//    void getPrivateContacts_whenNoContacts_shouldReturnEmptyList() {
//        UUID ownerId = UUID.randomUUID();
//        List<PrivateContactResponseDto> emptyList = Collections.emptyList();
//
//        when(generalContactOrchestrator.findAllPrivateByOwnerId(ownerId)).thenReturn(emptyList);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.getPrivateContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(emptyList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findAllPrivateByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPrivateContacts() should handle contactFacade exception")
//    void getPrivateContacts_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//
//        when(generalContactOrchestrator.findAllPrivateByOwnerId(ownerId)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.getPrivateContacts(ownerId));
//        verify(generalContactOrchestrator, times(1)).findAllPrivateByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPublicContacts() should return 200 OK with list of public contacts")
//    void getPublicContacts_whenSuccessful_shouldReturn200WithContacts() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> expectedContacts = List.of(
//                new PublicContactResponseDto("email", "secondary@example.com", true),
//                new PublicContactResponseDto("phone_number", "+0987654321", true)
//        );
//
//        when(generalContactOrchestrator.findAllPublicByOwnerId(ownerId)).thenReturn(expectedContacts);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getPublicContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedContacts, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findAllPublicByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPublicContacts() should return empty list when no public contacts found")
//    void getPublicContacts_whenNoContacts_shouldReturnEmptyList() {
//        UUID ownerId = UUID.randomUUID();
//        List<PublicContactResponseDto> emptyList = Collections.emptyList();
//
//        when(generalContactOrchestrator.findAllPublicByOwnerId(ownerId)).thenReturn(emptyList);
//
//        ResponseEntity<List<PublicContactResponseDto>> response = contactController.getPublicContacts(ownerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(emptyList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).findAllPublicByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT getPublicContacts() should handle contactFacade exception")
//    void getPublicContacts_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//
//        when(generalContactOrchestrator.findAllPublicByOwnerId(ownerId)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.getPublicContacts(ownerId));
//        verify(generalContactOrchestrator, times(1)).findAllPublicByOwnerId(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT updateContact() should return 200 OK with updated contact")
//    void updateContact_whenValidContact_shouldReturn200WithUpdatedContact() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactType type = ContactType.EMAIL;
//        ContactUpdateDto updateDto = new ContactUpdateDto(1L, type,"test@updated.com" , false);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, type.toString(), "test@updated.com", true, false);
//
//        when(generalContactOrchestrator.update(ownerId, updateDto)).thenReturn(responseDto);
//
//        ResponseEntity<PrivateContactResponseDto> response = contactController.updateContact(ownerId, updateDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(responseDto, response.getBody());
//        verify(generalContactOrchestrator, times(1)).update(ownerId, updateDto);
//    }
//
//    @Test
//    @DisplayName("UT updateContact() should handle contactFacade exception")
//    void updateContact_whenFacadeThrows_shouldThrowException() throws IOException {
//        UUID ownerId = UUID.randomUUID();
//        ContactUpdateDto updateDto = new ContactUpdateDto(1L, ContactType.EMAIL, "test@updated.com", true);
//
//        when(generalContactOrchestrator.update(ownerId, updateDto)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.updateContact(ownerId, updateDto));
//        verify(generalContactOrchestrator, times(1)).update(ownerId, updateDto);
//    }
//
//    @Test
//    @DisplayName("UT updateAllContacts() should return 200 OK with list of updated contacts")
//    void updateAllContacts_whenValidContactsList_shouldReturn200WithUpdatedContacts() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactUpdateDto> updateDtoList = List.of(
//                new ContactUpdateDto(1L, ContactType.EMAIL, "test1@updated.com", false),
//                new ContactUpdateDto(2L, ContactType.EMAIL, "test2@updated.com", true)
//        );
//
//        ContactUpdateDtoList dtoList = new ContactUpdateDtoList(updateDtoList);
//
//        List<PrivateContactResponseDto> responseDtoList = List.of(
//                new PrivateContactResponseDto(1L, "EMAIL", "test1@updated.com", true, false),
//                new PrivateContactResponseDto(2L, "EMAIL", "test2@updated.com", false, true)
//        );
//
//        when(generalContactOrchestrator.updateAll(ownerId, updateDtoList)).thenReturn(responseDtoList);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.updateAllContacts(ownerId, dtoList);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(responseDtoList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).updateAll(ownerId, updateDtoList);
//    }
//
//    @Test
//    @DisplayName("UT updateAllContacts() should return 200 OK with empty list when input is empty")
//    void updateAllContacts_whenEmptyList_shouldReturn200WithEmptyList() {
//        UUID ownerId = UUID.randomUUID();
//        List<PrivateContactResponseDto> emptyResponseList = Collections.emptyList();
//        ContactUpdateDtoList emptyUpdateList = new ContactUpdateDtoList(Collections.emptyList());
//
//        when(generalContactOrchestrator.updateAll(ownerId, emptyUpdateList.contacts())).thenReturn(emptyResponseList);
//
//        ResponseEntity<List<PrivateContactResponseDto>> response = contactController.updateAllContacts(ownerId, emptyUpdateList);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(emptyResponseList, response.getBody());
//        verify(generalContactOrchestrator, times(1)).updateAll(ownerId, emptyUpdateList.contacts());
//    }
//
//    @Test
//    @DisplayName("UT updateAllContacts() should handle contactFacade exception")
//    void updateAllContacts_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactUpdateDto> updateDtoList = List.of(
//                new ContactUpdateDto(1L, ContactType.EMAIL, "test1@updated.com", false),
//                new ContactUpdateDto(2L, ContactType.EMAIL, "test2@updated.com", true)
//        );
//
//        ContactUpdateDtoList dtoList = new ContactUpdateDtoList(updateDtoList);
//
//
//        when(generalContactOrchestrator.updateAll(ownerId, updateDtoList)).thenThrow(new RuntimeException("Test exception"));
//
//        assertThrows(RuntimeException.class, () -> contactController.updateAllContacts(ownerId, dtoList));
//        verify(generalContactOrchestrator, times(1)).updateAll(ownerId, updateDtoList);
//    }
//
//    @Test
//    @DisplayName("UT deleteContact() should return 204 NO_CONTENT when successful")
//    void deleteContact_whenSuccessful_shouldReturn204() {
//        UUID ownerId = UUID.randomUUID();
//        Long contactId = 1L;
//
//        doNothing().when(generalContactOrchestrator).delete(ownerId, contactId);
//
//        ResponseEntity<?> response = contactController.deleteContact(ownerId, contactId);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(generalContactOrchestrator, times(1)).delete(ownerId, contactId);
//    }
//
//    @Test
//    @DisplayName("UT deleteContact() should handle contactFacade exception")
//    void deleteContact_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//        Long contactId = 1L;
//
//        doThrow(new RuntimeException("Test exception")).when(generalContactOrchestrator).delete(ownerId, contactId);
//
//        assertThrows(RuntimeException.class, () -> contactController.deleteContact(ownerId, contactId));
//        verify(generalContactOrchestrator, times(1)).delete(ownerId, contactId);
//    }
//
//    @Test
//    @DisplayName("UT deleteAll() should return 204 NO_CONTENT when successful")
//    void deleteAll_whenSuccessful_shouldReturn204() {
//        UUID ownerId = UUID.randomUUID();
//
//        doNothing().when(generalContactOrchestrator).deleteAll(ownerId);
//
//        ResponseEntity<?> response = contactController.deleteAll(ownerId);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(generalContactOrchestrator, times(1)).deleteAll(ownerId);
//    }
//
//    @Test
//    @DisplayName("UT deleteAll() should handle contactFacade exception")
//    void deleteAll_whenFacadeThrows_shouldThrowException() {
//        UUID ownerId = UUID.randomUUID();
//
//        doThrow(new RuntimeException("Test exception")).when(generalContactOrchestrator).deleteAll(ownerId);
//
//        assertThrows(RuntimeException.class, () -> contactController.deleteAll(ownerId));
//        verify(generalContactOrchestrator, times(1)).deleteAll(ownerId);
//    }
//}
