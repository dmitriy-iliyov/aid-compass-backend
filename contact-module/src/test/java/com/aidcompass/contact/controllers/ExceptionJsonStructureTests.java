//package com.aidcompass.contact.controllers;
//
//
//import com.aidcompass.contact.models.dto.ContactCreateDto;
//import com.aidcompass.dto.PrivateContactResponseDto;
//import com.aidcompass.contact.facades.ContactFacade;
//import com.aidcompass.contracts.SystemContactFacade;
//import com.aidcompass.contact.validation.validators.impl.UniquenessValidatorImpl;
//import com.aidcompass.exceptions.ContactControllerAdvice;
//import com.aidcompass.mapper.ExceptionMapperImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.File;
//import java.util.UUID;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Disabled("временно до фикса непостоянной сортировки списка ErrorDto в ErrorUtils")
//@WebMvcTest(ContactController.class)
//@ContextConfiguration(classes = {ContactController.class})
//@Import({ContactControllerAdvice.class, ObjectMapper.class, ExceptionMapperImpl.class, UniquenessValidatorImpl.class})
//public class ExceptionJsonStructureTests {
//
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    ContactFacade contactFacade;
//
//    @MockitoBean
//    SystemContactFacade systemContactFacade;
//
//    final static String BASE_URL = "/api/v1/contacts";
//    final static String CREATE_CONTACT_URL = BASE_URL + "/%s";
//    final static String CREATE_CONTACTS_URL = BASE_URL + "/batch/%s";
//    final static String LINK_EMAIL_TO_ACCOUNT_URL = BASE_URL + "/link-email/%d/%s";
//    final static String GET_PRIMARY_URL = BASE_URL + "/primary/%s";
//    final static String GET_SECONDARY_URL = BASE_URL + "/secondary/%s";
//    final static String GET_PRIVATE_URL = BASE_URL + "/private/%s";
//    final static String GET_PUBLIC_URL = BASE_URL + "/public/%s";
//    final static String UPDATE_CONTACT_URL = BASE_URL + "/%s";
//    final static String UPDATE_ALL_CONTACTS_URL = BASE_URL + "/%s";
//    final static String DELETE_CONTACT_URL = BASE_URL + "/%s/%d";
//    final static String DELETE_ALL_CONTACT_URL = BASE_URL + "/%s/%d";
//
//
//    @Test
//    @DisplayName("IT createContact() when email is blank should return 400")
//    void createContact_whenBlankEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/blank_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when email is empty should return 400")
//    void createContact_whenEmptyEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/empty_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when email is \\t should return 400")
//    void createContact_whenSlashTEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/slashT_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when email is \\n should return 400")
//    void createContact_whenSlashNEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/slashN_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when phone number is blank should return 400")
//    void createContact_whenBlankPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/blank_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(2)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when phone number is empty should return 400")
//    void createContact_whenEmptyPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/empty_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(2)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when phone number is \\t should return 400")
//    void createContact_whenSlashTPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/slashT_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(2)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when phone number is \\n should return 400")
//    void createContact_whenSlashNPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/slashN_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(2)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//}
