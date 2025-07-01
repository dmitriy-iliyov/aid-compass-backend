//package com.aidcompass.contact.controllers;
//
//import com.aidcompass.contact.models.dto.ContactCreateDtoList;
//import com.aidcompass.contact.validation.validators.impl.UniquenessValidatorImpl;
//import com.aidcompass.mapper.ExceptionMapperImpl;
//import com.aidcompass.contact.models.dto.ContactCreateDto;
//import com.aidcompass.dto.PrivateContactResponseDto;
//import com.aidcompass.contact.facades.ContactFacade;
//import com.aidcompass.contracts.SystemContactFacade;
//import com.aidcompass.exceptions.ContactControllerAdvice;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.File;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ContactController.class)
//@Import({ContactControllerAdvice.class, ExceptionMapperImpl.class, ObjectMapper.class, UniquenessValidatorImpl.class})
//@ContextConfiguration(classes = {ContactController.class})
//public class ContactControllerIntegrationTests {
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
//    @DisplayName("IT createContact() when contact is valid email should return 201")
//    void createContact_whenEmailValid_shouldReturn201() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//        doReturn(false).when(systemContactFacade).existsByContactTypeAndContact(dto.type(), dto.contact());
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
//
//        verify(contactFacade, times(1)).save(ownerId, dto);
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when email is existed should return 400")
//    void createContact_whenEmailExisted_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//        doReturn(true).when(systemContactFacade).existsByContactTypeAndContact(dto.type(), dto.contact());
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email is in use!"));
//
//        verify(contactFacade, times(0)).save(ownerId, dto);
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when invalid email should return 400")
//    void createContact_whenInvalidEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email should be valid!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when long email should return 400")
//    void createContact_whenLongEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/long_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email length must be greater than 7 and less than 50!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when short email should return 400")
//    void createContact_whenShortEmail_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/short_email.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email length must be greater than 7 and less than 50!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
////    @Test
////    @DisplayName("IT createContact() when email is null should return 400")
////    void createContact_whenNullEmail_shouldReturn400() throws Exception {
////        UUID ownerId = UUID.randomUUID();
////        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/null_email.json"), ContactCreateDto.class);
////        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
////        String json = objectMapper.writeValueAsString(dto);
////
////        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
////
////        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(json))
////                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
////                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
////                .andExpect(jsonPath("$.properties.errors[0].message").value("Contact shouldn't be empty or blank!"));
////
////        verify(systemContactService, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
////        verifyNoMoreInteractions(contactFacade, systemContactService);
////    }
//
////    @Test
////    @DisplayName("IT createContact() when invalid contact type should return 400")
////    void createContact_whenInvalidContactType_shouldReturn400() throws Exception {
////        UUID ownerId = UUID.randomUUID();
////        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_contact_type.json"), ContactCreateDto.class);
////        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
////        String json = objectMapper.writeValueAsString(dto);
////
////        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
////
////        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(json))
////                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
////                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
////                .andExpect(jsonPath("$.properties.errors[0].message").value("Email should be valid!"));
////
////        verify(systemContactService, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
////        verifyNoMoreInteractions(contactFacade, systemContactService);
////    }
//
//    @Test
//    @DisplayName("IT createContact() when contact is valid phone number should return 201")
//    void createContact_whenPhoneNumberValid_shouldReturn201() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//        doReturn(false).when(systemContactFacade).existsByContactTypeAndContact(dto.type(), dto.contact());
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
//
//        verify(contactFacade, times(1)).save(ownerId, dto);
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when phone number is existed should return 400")
//    void createContact_whenPhoneNumberExisted_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//        doReturn(true).when(systemContactFacade).existsByContactTypeAndContact(dto.type(), dto.contact());
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Phone number is in use!"));
//
//        verify(contactFacade, times(0)).save(ownerId, dto);
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when invalid phone number should return 400")
//    void createContact_whenInvalidPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Phone number should be valid!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when long phone number should return 400")
//    void createContact_whenLongPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/long_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Phone number should be valid!"));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContact() when short phone number should return 400")
//    void createContact_whenShortPhoneNumber_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDto dto = objectMapper.readValue(new File("./src/main/resources/requests/invalid/short_phone_number.json"), ContactCreateDto.class);
//        PrivateContactResponseDto responseDto = new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false);
//        String json = objectMapper.writeValueAsString(dto);
//
//        doReturn(responseDto).when(contactFacade).save(ownerId, dto);
//
//        mockMvc.perform(post(CREATE_CONTACT_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Phone number should be valid!"));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
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
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItem(containsString("Contact shouldn't be empty or blank!"))));
//
//        verify(systemContactFacade, times(1)).existsByContactTypeAndContact(dto.type(), dto.contact());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when contacts are valid emails should return 201")
//    void createContacts_whenEmailsValid_shouldReturn201() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtos = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class)
//        );
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtos);
//
//        List<PrivateContactResponseDto> responseDtos = dtos.stream()
//                .map(dto -> new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false))
//                .collect(Collectors.toList());
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//        System.out.println(json);
//        doReturn(responseDtos).when(contactFacade).saveAll(ownerId, dtos);
//        doReturn(false).when(systemContactFacade).existsByContactTypeAndContact(any(), any());
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseDtos)));
//
//        verify(contactFacade, times(1)).saveAll(ownerId, dtos);
//        verify(systemContactFacade, times(dtos.size())).existsByContactTypeAndContact(any(), any());
//        verifyNoMoreInteractions(contactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when empty list should return 400")
//    void createContacts_whenEmptyList_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(Collections.emptyList());
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contacts"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("List of contacts can't be empty!"));
//
//        verifyNoInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when one email is invalid should return 400")
//    void createContacts_whenOneEmailInvalid_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtos = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_email.json"), ContactCreateDto.class)
//        );
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtos);
//
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contacts[1].contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email should be valid!"));
//
//        verify(systemContactFacade, times(dtos.size())).existsByContactTypeAndContact(any(), any());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when one email is existed should return 400")
//    void createContacts_whenOneEmailExisted_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtos = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email_2.json"), ContactCreateDto.class)
//        );
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtos);
//
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//
//        doReturn(false).when(systemContactFacade).existsByContactTypeAndContact(eq(dtos.get(0).type()), eq(dtos.get(0).contact()));
//        doReturn(true).when(systemContactFacade).existsByContactTypeAndContact(eq(dtos.get(1).type()), eq(dtos.get(1).contact()));
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("contacts[1].contact"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email is in use!"));
//
//        verify(systemContactFacade, times(dtos.size())).existsByContactTypeAndContact(any(), any());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() with mixed valid contact types should return 201")
//    void createContacts_withMixedValidContactTypes_shouldReturn201() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtos = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/save_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/save_phone.json"), ContactCreateDto.class)
//        );
//        List<PrivateContactResponseDto> responseDtos = dtos.stream()
//                .map(dto -> new PrivateContactResponseDto(1L, dto.type().toString(), dto.contact(), false, false))
//                .collect(Collectors.toList());
//
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtos);
//
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//
//        doReturn(responseDtos).when(contactFacade).saveAll(ownerId, dtos);
//        doReturn(false).when(systemContactFacade).existsByContactTypeAndContact(any(), any());
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseDtos)));
//
//        verify(contactFacade, times(1)).saveAll(ownerId, dtos);
//        verify(systemContactFacade, times(dtos.size())).existsByContactTypeAndContact(any(), any());
//        verifyNoMoreInteractions(contactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when multiple validation errors should return all errors")
//    void createContacts_whenMultipleValidationErrors_shouldReturnAllErrors() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        List<ContactCreateDto> dtos = List.of(
//                objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/invalid/short_email.json"), ContactCreateDto.class),
//                objectMapper.readValue(new File("./src/main/resources/requests/invalid/invalid_phone_number.json"), ContactCreateDto.class)
//        );
//        ContactCreateDtoList wrappedDtoList = new ContactCreateDtoList(dtos);
//
//        String json = objectMapper.writeValueAsString(wrappedDtoList);
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(3)))
//                .andExpect(jsonPath("$.properties.errors[*].field", containsInAnyOrder(
//                        "contacts[0].contact",
//                        "contacts[1].contact",
//                        "contacts[2].contact"
//                )))
//                .andExpect(jsonPath("$.properties.errors[*].message", containsInAnyOrder(
//                        "Email should be valid!",
//                        "Email length must be greater than 7 and less than 50!",
//                        "Phone number should be valid!"
//                )));
//
//        verify(systemContactFacade, times(dtos.size())).existsByContactTypeAndContact(any(), any());
//        verifyNoMoreInteractions(contactFacade, systemContactFacade);
//    }
//
//    @Test
//    @DisplayName("IT createContacts() when null list should return 400")
//    void createContacts_whenNullList_shouldReturn400() throws Exception {
//        UUID ownerId = UUID.randomUUID();
//        String json = "null";
//
//        mockMvc.perform(post(CREATE_CONTACTS_URL.formatted(ownerId))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verifyNoInteractions(contactFacade, systemContactFacade);
//    }
//}
