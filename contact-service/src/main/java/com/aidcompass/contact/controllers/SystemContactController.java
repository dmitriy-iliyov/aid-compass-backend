package com.aidcompass.contact.controllers;

import com.aidcompass.contact.facades.SystemFacade;
import com.aidcompass.contact.facades.ServiceSynchronizationFacade;
import com.aidcompass.contact.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.contact.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact_type.models.ContactType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/system/v1/contacts")
@RequiredArgsConstructor
public class SystemContactController {

    private final ServiceSynchronizationFacade synchronizationFacade;
    private final SystemFacade systemFacade;


    // only for message-service
    @PatchMapping("/{contact_id}/confirm")
    public ResponseEntity<?> confirmContact(@PathVariable("contact_id") Long contactId) {
        systemFacade.confirmContactById(contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // only for auth-service
    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody @Valid SystemContactCreateDto dto) {
        synchronizationFacade.save(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // only for auth-service
    @PatchMapping("/confirm")
    public ResponseEntity<?> confirmEmail(@RequestBody @Valid SystemConfirmationRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("contact_id", synchronizationFacade.confirmContact(requestDto)));
    }

    // only for auth-service
    @PatchMapping
    public ResponseEntity<?> updateContact(@RequestBody @Valid SystemContactUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(systemFacade.update(dto));
    }

    // only for auth-service
    @GetMapping("/{email}")
    public ResponseEntity<?> isEmailExist(@PathVariable("email") String contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(synchronizationFacade.existsByContactTypeAndContact(ContactType.EMAIL, contact));
    }
}