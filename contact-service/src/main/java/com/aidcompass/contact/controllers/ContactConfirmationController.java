package com.aidcompass.contact.controllers;

import com.aidcompass.contact.services.ContactFacade;
import com.aidcompass.contact.services.SystemContactFacade;
import com.aidcompass.contact.services.SystemContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contacts/{contact_id}")
@RequiredArgsConstructor
public class ContactConfirmationController {

    private final SystemContactService systemContactService;


    // only for message-service
    @PatchMapping("/confirm")
    public ResponseEntity<?> confirmContact(@PathVariable("contact_id") Long contactId) {
        systemContactService.confirmContactById(contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}