package com.example.contact;

import com.example.contact.models.dto.ContactCreateDto;
import com.example.contact.models.dto.PrivateContactResponseDto;
import com.example.contact.services.ContactFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ContactControllerUnitTests {

    @Mock
    ContactFacade contactFacade;

    @InjectMocks
    ContactController contactController;

    static ObjectMapper objectMapper;


    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("UT createContact() should return 201 CREATED with valid email from JSON")
    void createContact_whenEmailValidFromJson_shouldReturn201() throws IOException {
        UUID ownerId = UUID.randomUUID();
        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
        PrivateContactResponseDto privateDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);

        when(contactFacade.save(ownerId, dto)).thenReturn(privateDto);

        ResponseEntity<PrivateContactResponseDto> response = contactController.createContact(ownerId, dto);

        assertEquals(privateDto, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(contactFacade, times(1)).save(ownerId, dto);
    }

    @Test
    @DisplayName("UT createContact() should handle contactFacade exception")
    void createContact_whenFacadeThrows_shouldThrowException() throws IOException {
        UUID ownerId = UUID.randomUUID();
        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);

        when(contactFacade.save(ownerId, dto)).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () -> contactController.createContact(ownerId, dto));
        verify(contactFacade, times(1)).save(ownerId, dto);
    }

    @Test
    @DisplayName("UT createContact() should return 201 CREATED with valid phone number from JSON")
    void createContact_whenPhoneValidFromJson_shouldReturn201() throws IOException {
        UUID ownerId = UUID.randomUUID();
        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class);
        PrivateContactResponseDto privateDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);

        when(contactFacade.save(ownerId, dto)).thenReturn(privateDto);

        ResponseEntity<PrivateContactResponseDto> response = contactController.createContact(ownerId, dto);

        assertEquals(privateDto, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(contactFacade, times(1)).save(ownerId, dto);
    }
}
